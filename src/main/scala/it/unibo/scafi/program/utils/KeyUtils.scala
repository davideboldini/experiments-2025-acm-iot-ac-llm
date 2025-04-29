package it.unibo.scafi.program.utils

import it.unibo.scafi.program.utils.Keys.{GEMINI_KEY, GITHUB_TOKEN, OPENROUTER_KEY}

import scala.io.Source
import scala.util.Using

object KeyUtils:

  private val keyFilePath = "keys/ApiKeys.txt"

  private lazy val fileKeys: Map[String, String] =
    Using(Source.fromFile(keyFilePath))(_.getLines().toList)
      .map(_.collect {
        case line if line.contains(":") =>
          val Array(k, v) = line.split(":", 2).map(_.trim)
          k -> v
      }.toMap)
      .getOrElse:
        println(s"Warning: Could not read API keys from $keyFilePath")
        Map.empty

  private def fromEnvOrFile(key: String): Option[String] =
    Option(System.getenv(key)).orElse(fileKeys.get(key))

  private def requireKey(key: Keys): String =
    fromEnvOrFile(key.toString).getOrElse:
      throw new IllegalStateException(
        s"Missing required API key '$key'. Not found in environment variables nor in $keyFilePath"
      )

  def geminiKey: String = requireKey(GEMINI_KEY)

  def openRouterKey: String = requireKey(OPENROUTER_KEY)

  def githubToken: String = requireKey(GITHUB_TOKEN)

  def get(apiName: String): Option[String] = fromEnvOrFile(apiName)
  
end KeyUtils

enum Keys:
  case GEMINI_KEY
  case OPENROUTER_KEY
  case GITHUB_TOKEN

  override def toString: String =
    this match
      case GEMINI_KEY => "GEMINI_API_KEY"
      case OPENROUTER_KEY => "OPENROUTER_API_KEY"
      case GITHUB_TOKEN => "GITHUB_TOKEN"
