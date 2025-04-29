package it.unibo.scafi.program.llm.openrouter.factory

import it.unibo.scafi.program.llm.CodeGeneratorService
import it.unibo.scafi.program.llm.openrouter.OpenRouterService
import it.unibo.scafi.program.llm.openrouter.models.OpenRouterModels

object OpenRouterFactory:
  
  val models: List[OpenRouterModels] = OpenRouterModels.values.toList
  
  def apply(): List[CodeGeneratorService] = 
    models.map(model => OpenRouterService(model))

