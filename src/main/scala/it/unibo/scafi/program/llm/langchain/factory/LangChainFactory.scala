package it.unibo.scafi.program.llm.langchain.factory

import it.unibo.scafi.program.llm.langchain.models.LangChainModel

trait LangChainFactory:

  def apply(baseUrl: Option[String] = None): List[LangChainModel]

