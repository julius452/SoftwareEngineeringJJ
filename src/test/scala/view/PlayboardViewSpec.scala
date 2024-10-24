package view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayboardViewSpec extends AnyWordSpec {
  val expectedMessage: String =
    "Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht\nHier ist ein erster Entwurf unseres Spielfeldes:\n\n"

  val playboard: Array[String] = Array.fill(40)("00")
  playboard(0) = "ST"
  playboard(10) = "ST"
  playboard(20) = "ST"
  playboard(30) = "ST"

  val expectedPlayboard: String =
    """ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00""".stripMargin

  val house1: Array[String] = Array.fill(4)("HH")
  val house2: Array[String] = Array.fill(4)("HH")
  val house3: Array[String] = Array.fill(4)("HH")
  val house4: Array[String] = Array.fill(4)("HH")

  val expectedHouses: String =
    "\n\nHäuser der Spieler:\nPlayer1 House: HH | HH | HH | HH\nPlayer2 House: HH | HH | HH | HH\nPlayer3 House: HH | HH | HH | HH\nPlayer4 House: HH | HH | HH | HH"

  "PlayboardView" should {
    "print the welcome message correctly" in {
      val message = PlayboardView.printMessage()
      message should equal(expectedMessage)
    }

    "print the playboard correctly" in {
      val playboardOutput = PlayboardView.printPlayBoard(playboard)
      playboardOutput should equal(expectedPlayboard)
    }

    "print the houses correctly" in {
      val housesOutput = PlayboardView.printHouses(house1, house2, house3, house4)
      housesOutput should equal(expectedHouses)
    }

    "print the playboard and houses correctly" in {
      val expectedOutput = expectedMessage + expectedPlayboard + expectedHouses
      val output = PlayboardView.printBoard(playboard, house1, house2, house3, house4)
      output should equal(expectedOutput)
    }
  }
}