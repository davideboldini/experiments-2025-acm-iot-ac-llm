package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.LocalAiLangChainModel
import it.unibo.scafi.program.llm.langchain.models.enums.OllamaModels

object LocalAIFactory extends LangChainFactory:
  
  private val models: List[OllamaModels] =
    OllamaModels.values.toList
    
  override def apply(baseUrl: Option[String]): List[LocalAiLangChainModel] = 
    models.map(model => LocalAiLangChainModel(baseUrl.get, model.toString))
  

