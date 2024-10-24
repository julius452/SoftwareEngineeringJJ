package controller

import model.PlayboardModel
import org.junit.Assert._
import org.junit.Test
import view.PlayboardView
class PlayBoardControllerJUnit {
  val expectedMessage: String =
    "Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht\nHier ist ein erster Entwurf unseres Spielfeldes:\n\n"

  val expectedPlayboard: String =
    "ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00"

  val house1: Array[String] = Array.fill(4)("HH")
  val house2: Array[String] = Array.fill(4)("HH")
  val house3: Array[String] = Array.fill(4)("HH")
  val house4: Array[String] = Array.fill(4)("HH")

  val expectedHouses: String =
    "\n\nHäuser der Spieler:\nPlayer1 House: HH | HH | HH | HH\nPlayer2 House: HH | HH | HH | HH\nPlayer3 House: HH | HH | HH | HH\nPlayer4 House: HH | HH | HH | HH"


  @Test
    def testPrintBoard(): Unit = {
      val model = new PlayboardModel
      val controller = new PlayboardController(model, PlayboardView)
      val output = controller.printBoard()

      val expectedOutput = expectedMessage + expectedPlayboard + expectedHouses

      assertEquals(expectedOutput, output)
    }
}
