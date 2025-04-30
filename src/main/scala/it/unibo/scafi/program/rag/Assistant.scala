package it.unibo.scafi.program.rag

import dev.langchain4j.service.{TokenStream, UserMessage}

trait Assistant:
    def chat(@UserMessage userMessage: String): TokenStream
