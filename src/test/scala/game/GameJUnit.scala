package game

import org.junit.Assert._
import org.junit.Test

class GameJUnit {
  @Test
  def testMain(): Unit = {
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

    assertEquals(expectedOutput, gameOutput)
  }
}
