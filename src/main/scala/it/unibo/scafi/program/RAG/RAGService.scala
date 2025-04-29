package it.unibo.scafi.program.RAG

import it.unibo.scafi.program.llm.langchain.models.LangChainModel

class RAGService (val models: List[LangChainModel], val documents: List[String]):
  
    def build(): List[RAG] =
      val rags = for
        model <- models
        document <- documents
      yield 
        RAG(model, document)
        
      rags
