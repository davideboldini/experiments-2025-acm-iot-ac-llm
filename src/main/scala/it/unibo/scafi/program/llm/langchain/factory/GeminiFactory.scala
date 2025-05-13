package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.{GeminiLangChainModel}
import it.unibo.scafi.program.llm.{GeminiService}

object GeminiFactory extends LangChainFactory:

  private val loaders: List[GeminiService] = List(
    GeminiService.flash(GeminiService.Version.V1_5),
    GeminiService.flashExp(GeminiService.Version.V2_0),
    GeminiService.proExp(GeminiService.Version.V2_0),
  )

  override def apply(baseUrl: Option[String] = None): List[GeminiLangChainModel] =
    loaders.map(loader => GeminiLangChainModel(loader.apiKey, loader.model))
