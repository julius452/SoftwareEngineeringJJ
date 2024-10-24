package game

import controller.PlayboardController
import model.PlayboardModel
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import view.PlayboardView

class GameSpec extends AnyWordSpec {

  "Game" should {

    "initialize and print the playboard correctly" in {
      // Capture the output of the Game object
      val outputStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outputStream) {
        Game.main(Array.empty)
      }
      val gameOutput = outputStream.toString.trim

      val expectedMessage: String =
        "Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht\nHier ist ein erster Entwurf unseres Spielfeldes:\n\n"

      val expectedPlayboard: String =
        "ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | ST | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00"

      val expectedHouses: String =
        "\n\nHäuser der Spieler:\nPlayer1 House: HH | HH | HH | HH\nPlayer2 House: HH | HH | HH | HH\nPlayer3 House: HH | HH | HH | HH\nPlayer4 House: HH | HH | HH | HH"

      val expectedOutput = expectedMessage + expectedPlayboard + expectedHouses

      gameOutput should equal(expectedOutput)
    }
  }
}