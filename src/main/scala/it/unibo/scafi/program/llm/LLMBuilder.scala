package it.unibo.scafi.program.llm

import it.unibo.scafi.program.llm.langchain.LangChainService
import it.unibo.scafi.program.llm.langchain.factory.{GeminiFactory, GitHubFactory, LocalAIFactory, OllamaFactory, XInferenceFactory}
import it.unibo.scafi.program.llm.langchain.models.LangChainModel
import it.unibo.scafi.program.llm.openrouter.OpenRouterService
import it.unibo.scafi.program.llm.openrouter.models.{OpenRouterEnums, OpenRouterModel}

class LLMBuilder(val subMethod: String, val provider:String, val baseUrl: Option[String] = None):

  def build(): List[CodeGeneratorService] = (subMethod, provider) match
    case ("LANGCHAIN", _) => buildLangChainLLM()
    case ("OPENROUTER", _) => buildOpenRouterLLM()

  private def buildLangChainLLM(): List[LangChainService] =
    getLangChainModels().map(model => LangChainService(model))

  private def buildOpenRouterLLM(): List[OpenRouterService] =
    getOpenRouterModels().map(model => OpenRouterService(model))
  
  def getLangChainModels(): List[LangChainModel] = provider match
    case "GEMINI" => GeminiFactory.apply()
    case "GITHUB" => GitHubFactory.apply()
    case "LOCALAI" => LocalAIFactory.apply(baseUrl)
    case "OLLAMA" => OllamaFactory.apply(baseUrl)
    case "XINFERENCE" => XInferenceFactory.apply(baseUrl)
    
  private def getOpenRouterModels(): List[OpenRouterModel] =
    OpenRouterEnums.values.toList.map(model => OpenRouterModel(model.toString))
  


