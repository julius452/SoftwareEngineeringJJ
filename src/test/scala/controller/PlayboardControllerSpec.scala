package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.PlayboardModel
import view.PlayboardView

class PlayboardControllerSpec extends AnyWordSpec {

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


  "PlayboardController" should {

    "initialize the playboard correctly" in {
      val model = new PlayboardModel
      val controller = new PlayboardController(model, PlayboardView)
      val output = controller.printBoard()

      val expectedOutput = expectedMessage + expectedPlayboard + expectedHouses

      output should equal(expectedOutput)
    }
  }
}