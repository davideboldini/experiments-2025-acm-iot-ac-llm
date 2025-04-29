package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.OllamaLangChainModel
import it.unibo.scafi.program.llm.langchain.models.enums.OllamaModels

object OllamaFactory extends LangChainFactory:
  
  private val models: List[OllamaModels] =
    OllamaModels.values.toList
    
  override def apply(baseUrl: Option[String]): List[OllamaLangChainModel] =
    models.map(model => OllamaLangChainModel(model.toString, baseUrl.get))
