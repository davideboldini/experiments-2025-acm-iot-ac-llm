package it.unibo.scafi.program.llm

import it.unibo.scafi.program.llm.langchain.LangChainService
import it.unibo.scafi.program.llm.langchain.factory.{GeminiFactory, GitHubFactory, LocalAIFactory, OllamaFactory, XInferenceFactory}
import it.unibo.scafi.program.llm.langchain.models.LangChainModel
import it.unibo.scafi.program.llm.openrouter.factory.OpenRouterFactory
import it.unibo.scafi.program.llm.openrouter.models.OpenRouterModels

class LLMBuilder(val subMethod: String, val provider:String, val baseUrl: Option[String] = None):

  def build(): List[CodeGeneratorService] = (subMethod, provider) match
    case ("LANGCHAIN", _) => buildLangChainLLM()
    case ("OPENROUTER", _) => buildOpenRouterLLM()

  private def buildLangChainLLM(): List[LangChainService] =
    getLangChainModels().map(model => LangChainService(model))

  private def buildOpenRouterLLM(): List[CodeGeneratorService] =
    OpenRouterFactory.apply()
  
  def getLangChainModels(): List[LangChainModel] = provider match
    case "GEMINI" => GeminiFactory.apply()
    case "GITHUB" => GitHubFactory.apply()
    case "LOCALAI" => LocalAIFactory.apply(baseUrl)
    case "OLLAMA" => OllamaFactory.apply(baseUrl)
    case "XINFERENCE" => XInferenceFactory.apply(baseUrl)
    
  def getOpenRouterModels(): List[OpenRouterModels] =
    OpenRouterModels.values.toList
  


