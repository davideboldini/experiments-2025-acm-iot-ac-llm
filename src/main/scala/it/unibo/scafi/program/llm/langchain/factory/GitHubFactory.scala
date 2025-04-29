package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.GitHubLangChainModel
import it.unibo.scafi.program.llm.langchain.models.enums.GitHubModels

object GitHubFactory extends LangChainFactory:

  private lazy val defaultApiKey: String = System.getenv("GITHUB_API_KEY") match
    case null => throw new RuntimeException("GITHUB_API_KEY is not set")
    case apiKey => apiKey

  private val models: List[GitHubModels] = 
    GitHubModels.values.toList
  
  override def apply(baseUrl: Option[String] = None): List[GitHubLangChainModel] = 
    models.map(model => GitHubLangChainModel(defaultApiKey, model.toString))

