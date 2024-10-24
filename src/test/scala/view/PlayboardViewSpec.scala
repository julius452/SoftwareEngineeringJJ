package view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.PlayboardModel
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PlayboardViewSpec extends AnyWordSpec {

  // Hilfsfunktion zum Abfangen der Konsolenausgabe
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

  "PlayboardView" should {

    "print the playboard correctly" in {
      val model = new PlayboardModel()
      model.initializePlayboard()
      val output = captureOutput {
        PlayboardView.printPlayBoard(model.getPlayboard)
      }

      // Überprüfen, ob die Ausgabe des Spielfelds korrekt ist
      output.trim should be("ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 ")
    }

    "print the houses correctly" in {
      val house1 = Array("HH", "HH","HH","HH")
      val house2 = Array("HH", "HH","HH","HH")
      val house3 = Array("HH", "HH","HH","HH")
      val house4 = Array("HH", "HH","HH","HH")


      val output = captureOutput {
        PlayboardView.printHouses(house1,house2, house3, house4)
      }

      // Überprüfen, ob die Ausgabe der Häuser korrekt ist
      output should include ("Player1 House: HH | HH | HH | HH")
      output should include ("Player2 House: HH | HH | HH | HH")
      output should include ("Player3 House: HH | HH | HH | HH")
      output should include ("Player4 House: HH | HH | HH | HH")

    }
  }
}