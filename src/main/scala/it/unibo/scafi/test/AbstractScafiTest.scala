package it.unibo.scafi.test

import io.circe.generic.auto.*
import io.circe.parser.*
import it.unibo.scafi.Prompts
import it.unibo.scafi.program.rag.RAG
import it.unibo.scafi.program.llm.langchain.models.LangChainModel
import it.unibo.scafi.program.llm.{CodeGeneratorService, LLMBuilder}
import it.unibo.scafi.test.FunctionalTestIncarnation.Network
import it.unibo.scafi.test.ScafiTestResult.{CompilationFailed, GenericFailure}
import it.unibo.scafi.test.ScafiTestUtils.{buildProgram, executeFromString}

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.util.boundary.break
import scala.util.{Try, Using, boundary}

final case class ScafiProgram(program: String)

abstract class AbstractScafiProgramTest(
    private val knowledgePaths: List[String],
    private val promptsFilePath: String,

    private val runs: Int = 5,
    private val raw: Boolean = false,
):

  private lazy val candidatePrompts =
    decode[Prompts](Source.fromResource(promptsFilePath).mkString) match
      case Right(prompts) => prompts
      case Left(error) => throw new RuntimeException(s"Failed to decode prompts $error")

  private def programSpecification(
      knowledge: String,
      promptSpecification: String,
      model: CodeGeneratorService,
  ): ExecutionContext ?=> Future[ScafiProgram] =
    if !raw then model.generateMain(knowledge, promptSpecification).map(ScafiProgram(_))
    else model.generateRaw(knowledge, "", promptSpecification).map(ScafiProgram(_))


  private def executeScafiProgram(
      programUnderTest: ScafiProgram,
      preamble: String,
      post: String,
  ): Either[ScafiTestResult, Network] =
    val builtProgram = buildProgram(programUnderTest.program, preamble, post)
    val res = Try { executeFromString[Network](builtProgram) }.toEither.left.map(_ =>
      CompilationFailed(programUnderTest.program),
    )
    res

  def baselineWorkingProgram(): String

  def programTests(program: String, producedNet: Network): ScafiTestResult

  def postAction(): String = ""

  def preAction(): String = ""

  def testCase: String

  private def processLLM(n: Int, prompt: String, knowledgeFile: String, model: CodeGeneratorService)
                            (using ExecutionContext): Future[SingleTestResult] =
    Future(Using(Source.fromResource(knowledgeFile))(_.mkString).toEither)
      .flatMap:
        case Left(error) => Future.successful(SingleTestResult(testCase, n, knowledgeFile, model.toString, GenericFailure(error.getMessage)))
        case Right(knowledge) => processTest(n, prompt, knowledge, model)

  private def processRAG(n: Int, prompt: String, knowledgeFile: String, rag: CodeGeneratorService)
                            (using ExecutionContext): Future[SingleTestResult] =
    processTest(n, prompt, knowledgeFile, rag)

  private def processTest(n: Int, prompt: String, knowledge: String, model: CodeGeneratorService)
                         (using ExecutionContext): Future[SingleTestResult] =
    programSpecification(knowledge, prompt, model).map: currentProgram =>
      val result = executeScafiProgram(currentProgram, preAction(), postAction())
        .map(programTests(currentProgram.program, _))
        .fold(identity, identity)

      SingleTestResult(testCase, n, knowledge, model.toString, result)
  
  
  def executeTest(method: String, subMethod: String, langChainType: String, ngrokAddress: String): ExecutionContext ?=> Seq[Future[SingleTestResult]] =
    boundary:
      // Execute baseline test
      val baselineProgram = ScafiProgram(baselineWorkingProgram())
      val baselineResult = executeScafiProgram(baselineProgram, preAction(), postAction())
      if baselineResult.isLeft then
        println(s"Failed to compile baseline program: ${baselineResult.left}")
        break(Seq())

      val baselineNetwork = baselineResult.getOrElse(throw new RuntimeException("Baseline program failed to compile"))
      val baselineTestResult = programTests(baselineProgram.program, baselineNetwork)
      if !baselineTestResult.isInstanceOf[ScafiTestResult.Success] then
        println(s"Baseline test failed: $baselineTestResult")
        break(Seq())

      // Execute LLM or RAG tests
      executeTests(method, subMethod, langChainType, ngrokAddress)

  private def executeTests(
                            method: String,
                            subMethod: String,
                            langChainType: String,
                            ngrokAddress: String
                          ): ExecutionContext ?=> Seq[Future[SingleTestResult]] =

    val llmService = LLMBuilder(subMethod, langChainType, Option.apply(ngrokAddress))

    val models = method match
      case "LLM" => llmService.build()
      case "RAG" => llmService.getLangChainModels()

    for
      n <- 0 until runs
      prompt <- candidatePrompts.prompts
      knowledgeFile <- knowledgePaths
      result <- models.collect:
        case model: CodeGeneratorService if method == "LLM" =>
          processLLM(n, prompt, knowledgeFile, model)
        case model: LangChainModel if method == "RAG" =>
          processRAG(n, prompt, knowledgeFile, RAG(model, knowledgeFile))
    yield result

end AbstractScafiProgramTest
