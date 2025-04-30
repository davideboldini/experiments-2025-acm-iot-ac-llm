package it.unibo.scafi.test.scalaTest

import it.unibo.scafi.program.utils.UserUtils
import org.scalatest.funspec.AnyFunSpec

import java.io.ByteArrayInputStream

class InputSpec extends AnyFunSpec:

  describe("An user input"):
    describe("when reading something not in the valid options"):
      it("should raise an IllegalArgumentException"):
        val simulatedInput = new ByteArrayInputStream("Test\n".getBytes)

        Console.withIn(simulatedInput):
          assertThrows[IllegalArgumentException]:
            UserUtils.getUserChoice("Choose execution method (LLM/RAG): ", Set("LLM", "RAG"))

    describe("when reading a valid option"):
      it("should return the correct choice in uppercase"):
        val simulatedInput = new ByteArrayInputStream("llm\n".getBytes)

        Console.withIn(simulatedInput):
          val result = UserUtils.getUserChoice("Choose execution method (LLM/RAG): ", Set("LLM", "RAG"))
          assert(result == "LLM")

