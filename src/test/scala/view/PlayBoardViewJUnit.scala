package view

import org.junit.Assert._
import org.junit.Test

class PlayBoardViewJUnit {
  val expectedMessage: String =
    "Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht\nHier ist ein erster Entwurf unseres Spielfeldes:\n\n"

  val playboard: Array[String] = Array.fill(40)("00")
  playboard(0) = "ST"
  playboard(10) = "ST"
  playboard(20) = "ST"
  playboard(30) = "ST"

  val expectedPlayboard: String =
    "ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00"

  val house1: Array[String] = Array.fill(4)("HH")
  val house2: Array[String] = Array.fill(4)("HH")
  val house3: Array[String] = Array.fill(4)("HH")
  val house4: Array[String] = Array.fill(4)("HH")

  val expectedHouses: String =
    "\n\nHäuser der Spieler:\nPlayer1 House: HH | HH | HH | HH\nPlayer2 House: HH | HH | HH | HH\nPlayer3 House: HH | HH | HH | HH\nPlayer4 House: HH | HH | HH | HH"

  @Test
    def testPrintMessage(): Unit = {
      val message = PlayboardView.printMessage()

      assertEquals(expectedMessage, message)
    }

  @Test
    def testPrintPlayBoard(): Unit = {
      val playboardOutput = PlayboardView.printPlayBoard(playboard)

      assertEquals(expectedPlayboard, playboardOutput)
    }

  @Test
    def testPrintHouses(): Unit = {
      val housesOutput = PlayboardView.printHouses(house1, house2, house3, house4)

      assertEquals(expectedHouses, housesOutput)
    }

  @Test
    def testPrintBoard(): Unit = {
      val expectedOutput = expectedMessage + expectedPlayboard + expectedHouses
      val output = PlayboardView.printBoard(playboard, house1, house2, house3, house4)

      assertEquals(expectedOutput, output)
    }
}
