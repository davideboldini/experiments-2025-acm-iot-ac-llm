package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.XinferenceLangChainModel
import it.unibo.scafi.program.llm.langchain.models.enums.XinferenceModels

object XInferenceFactory extends LangChainFactory:

  private val models: List[XinferenceModels] =
    XinferenceModels.values.toList

  override def apply(baseUrl: Option[String]): List[XinferenceLangChainModel] =
    models.map(model => XinferenceLangChainModel(model.toString, baseUrl.get))
