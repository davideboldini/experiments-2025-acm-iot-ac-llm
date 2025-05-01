package it.unibo.scafi.program.llm.openrouter.models

import dev.langchain4j.model.openai.OpenAiStreamingChatModel
import it.unibo.scafi.program.utils.KeyUtils

class OpenRouterModel(val modelName: String):

  private val url = s"https://openrouter.ai/api/v1"
  private val defaultApiKey: String = KeyUtils.openRouterKey

  def build(): OpenAiStreamingChatModel =
    OpenAiStreamingChatModel.builder()
      .baseUrl(url)
      .apiKey(defaultApiKey)
      .modelName(modelName)
      .build()

  override def toString: String =
    s"OpenRouter model: $modelName"
