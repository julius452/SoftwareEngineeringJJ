package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.PlayboardModel
import view.PlayboardView

import java.io.ByteArrayOutputStream
import java.io.PrintStream
class PlayboardControllerSpec extends AnyWordSpec {

  // Hilfsmethode zum Abfangen der Konsolenausgabe
  def captureOutput(testCode: => Unit): String = {
    val outputStream = new ByteArrayOutputStream()
    val printStream = new PrintStream(outputStream)
    val originalOut = System.out
    try {
      System.setOut(printStream)
      testCode
      printStream.flush()
      outputStream.toString
    } finally {
      System.setOut(originalOut)
    }
  }

  "PlayboardController" should {

    "initialize the model and print the board" in {
      val model = new PlayboardModel()
      val controller = new PlayboardController(model, PlayboardView)

      val output = captureOutput {
        controller.printBoard()
      }

      // Überprüfen, ob die korrekten Elemente im Output sind
      output should include("Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht")
      output should include("ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00")
      output should include("Player1 House: HH | HH | HH | HH")
      output should include("Player2 House: HH | HH | HH | HH")
      output should include("Player3 House: HH | HH | HH | HH")
      output should include("Player4 House: HH | HH | HH | HH")
    }
  }
}