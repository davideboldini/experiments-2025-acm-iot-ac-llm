package it.unibo.scafi.program.utils

import scala.io.StdIn.readLine

object UserUtils:
  def getUserChoice(prompt: String, validOptions: Set[String]): String =
    val choice = readLine(prompt).trim.toUpperCase
    if validOptions.contains(choice) then choice
    else
      throw new IllegalArgumentException(s"Invalid choice! You must enter one of: ${validOptions.mkString(", ")}")
