package it.unibo.scafi.test.scalaTest

import it.unibo.scafi.program.GradientWithDistanceFromSourceAndObstacles
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.Assertion

import java.nio.file.{Files, Paths}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.*
import java.util.concurrent.Executors

class TokenSpec extends AnyFunSpec:

  given ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
  val keyFilePath = Paths.get("keys", "ApiKeys.txt")

  describe("Environment with token file or token global variable"):

    it("should raise or not raise IllegalStateException depending on the key file presence"):

      val tests = List(GradientWithDistanceFromSourceAndObstacles())
      val method = "LLM"
      val subMethod = "LANGCHAIN"
      val langChainType = "GEMINI"
      val ngrokAddress = ""

      def testExecution(): Assertion =
        val result = Await.result(
          Future.sequence {
            tests.map(e => Future.sequence(e.executeTest(method, subMethod, langChainType, ngrokAddress)))
          }.map(_.flatten),
          1.minute
        )
        assert(result.nonEmpty || result.isEmpty) // forza l'uso del valore, evita warning

      if Files.exists(keyFilePath) then
        try
          val assertion = testExecution()
          assertion
        catch
          case ex: Throwable =>
            fail(s"Unexpected exception: ${ex.getMessage}")
      else
      assertThrows[IllegalStateException]:
        testExecution()

