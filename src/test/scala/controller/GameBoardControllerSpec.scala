package controller

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import model.{GameBoard, Piece, GameState, Player}
import controller.GameBoardController

class GameBoardControllerSpec extends AnyWordSpec {

  val expectedPlayboard: String =
    "00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00"

  "GameboardController" should {

    "initialize GameBoard correctly" in {
      val controller = new GameBoardController
      val gameBoard= controller.initializeGameBoard()
      // Überprüfen, ob das Array die richtige Länge hat
      gameBoard.fields.length shouldEqual 40

      // Überprüfen, ob alle Felder auf "00" gesetzt sind
      all (gameBoard.fields) shouldEqual "00"
    }
  }
}
