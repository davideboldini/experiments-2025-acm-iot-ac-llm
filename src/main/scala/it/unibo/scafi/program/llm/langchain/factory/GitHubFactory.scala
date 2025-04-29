package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.GitHubLangChainModel
import it.unibo.scafi.program.llm.langchain.models.enums.GitHubModels
import it.unibo.scafi.program.utils.KeyUtils

object GitHubFactory extends LangChainFactory:

  private lazy val defaultApiKey: String = KeyUtils.githubToken

  private val models: List[GitHubModels] = 
    GitHubModels.values.toList
  
  override def apply(baseUrl: Option[String] = None): List[GitHubLangChainModel] =
    models.map(model => GitHubLangChainModel(defaultApiKey, model.toString))

