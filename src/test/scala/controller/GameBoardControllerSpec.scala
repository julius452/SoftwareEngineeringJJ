package controller

import model.{Field, GameBoard, GameState, Piece, Player, Dice}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameBoardControllerSpec extends AnyWordSpec with Matchers {

  "GameBoardController" should {
    val controller = new GameBoardController

    "initialize GameBoard correctly" in {
      val board = new Array[Field](40)

      for (i <- 0 until 40) {
        if (i % 10 == 0) {
          board(i) = Field(value = "ST", position = i, isOccupied = false, piece = None, isStartField = true, isHouseField = false)
        } else {
          board(i) = Field(value = "00", position = i, isOccupied = false, piece = None, isStartField = false, isHouseField = false)
        }
      }

      controller.initializeGameBoard().fields shouldBe board
    }

    "move a piece correctly" in {
      val player = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = true, Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

      controller.movePiece(gameState, piece, 5)
      piece.traveledFields shouldBe 5
      gameBoard.fields(5).piece shouldBe Some(piece)
    }



    "throw a player out correctly" in {
      val pieces = Array.ofDim[Piece](4)
      val startHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false))

      for (i <- pieces.indices) {
        pieces(i) = Piece(Player("A", "Player1", pieces, Array(), Array(), startPosition = 0), i + 1, traveledFields = 0, isInHome = false, isOnField = false, startHouses(i))
        startHouses(i).piece = Some(pieces(i))
        startHouses(i).isOccupied = true
      }

      val player1 = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val player2 = Player("B", "Player2", Array(), Array(), startHouses, startPosition = 0)

      val piece1 = Piece(player1, 1, traveledFields = 0, isInHome = false, isOnField = true, Field("00", 0, isOccupied = true, piece = None, isStartField = false, isHouseField = false))
      piece1.field = new Field("00", 0, isOccupied = true, Some(piece1), isStartField = false, isHouseField = false)

      val piece2 = Piece(player2, 1, traveledFields = 5, isInHome = false, isOnField = true, Field("00", 5, isOccupied = true, piece = None, isStartField = false, isHouseField = false))
      piece2.field = new Field("00", 5, isOccupied = true, Some(piece2), isStartField = false, isHouseField = false)
      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = true, piece = None, isStartField = false, isHouseField = false)))
      gameBoard.fields(0) = piece1.field
      gameBoard.fields(5) = piece2.field

      val gameState = GameState(List(player1, player2), player1, Dice(6), gameBoard, isRunning = true)

      controller.throwPlayerOut(player2, piece1, piece2.field, gameState)

      piece2.isOnField shouldBe false
      gameBoard.fields(5).piece.get.id shouldBe piece1.id
    }
  }
}