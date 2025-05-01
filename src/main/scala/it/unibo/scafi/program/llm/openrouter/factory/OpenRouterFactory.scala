package it.unibo.scafi.program.llm.openrouter.factory

import it.unibo.scafi.program.llm.openrouter.models.{OpenRouterEnums, OpenRouterModel}

object OpenRouterFactory:
  
  val models: List[OpenRouterEnums] = OpenRouterEnums.values.toList
  
  def apply(): List[OpenRouterModel] =
    models.map(model => OpenRouterModel(model.toString))

