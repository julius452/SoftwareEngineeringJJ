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

    "move a piece correctly out of startHouse" in {
      val player = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = false, Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))

      val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

      controller.movePiece(gameState, piece, 6)
      piece.traveledFields shouldBe 0
      gameBoard.fields(0).piece shouldBe Some(piece)
    }

    "move a piece correctly when traveling beyond field 39 into the player's house" in {
      val house = Array.fill(4)(Field("00", 0,isOccupied = false, piece = None, isStartField = false, isHouseField = true))

      for (i<- house.indices) {
        house(i) = house(i).copy(position = i)
      }
      val player = Player("A", "Player1", Array(), house, Array(), startPosition = 0)
      val piece = Piece(player, 1, traveledFields = 38, isInHome = false, isOnField = true, Field("00", 38, isOccupied = true, piece = None, isStartField = false, isHouseField = false))

      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

      controller.movePiece(gameState, piece, 3)

      piece.isInHome shouldBe true
      piece.isOnField shouldBe false
      piece.field.position shouldBe 1

      controller.movePiece(gameState, piece, 8)

      piece.isInHome shouldBe true
      piece.isOnField shouldBe true
      piece.field.position shouldBe 0
    }

    "throw a player out correctly" in {
      val startHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false))
      val pieces = Array.tabulate(4)(i => Piece(Player("A", "Player1", Array(), Array(), startHouses, startPosition = 0), i + 1, traveledFields = 0, isInHome = false, isOnField = false, startHouses(i)))

      for (i <- pieces.indices) {
        startHouses(i).piece = Some(pieces(i))
        startHouses(i).isOccupied = true
      }

      val player1 = Player("A", "Player1", pieces, Array(), startHouses, startPosition = 0)
      val player2 = Player("B", "Player2", Array(), Array(), startHouses, startPosition = 0)

      val piece1Field = Field("00", 0, isOccupied = true, piece = None, isStartField = false, isHouseField = false)
      val piece1 = Piece(player1, 1, traveledFields = 0, isInHome = false, isOnField = true, piece1Field)
      piece1Field.piece = Some(piece1)

      val piece2Field = Field("00", 5, isOccupied = true, piece = None, isStartField = false, isHouseField = false)
      val piece2 = Piece(player2, 1, traveledFields = 5, isInHome = false, isOnField = true, piece2Field)
      piece2Field.piece = Some(piece2)

      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      gameBoard.fields(0) = piece1Field
      gameBoard.fields(5) = piece2Field

      val gameState = GameState(List(player1, player2), player1, Dice(6), gameBoard, isRunning = true)

      controller.throwPlayerOut(player2, piece1, piece2Field, gameState)

      piece2.isOnField shouldBe false
      piece2.traveledFields shouldBe 0
      piece2.field shouldBe startHouses(0)
      startHouses(0).isOccupied shouldBe true
      startHouses(0).piece shouldBe Some(piece2)

      piece1.field shouldBe piece2Field
      piece1.traveledFields shouldBe 6
      piece2Field.piece shouldBe Some(piece1)
    }

    "handle edge cases for movePiece" in {
      val player = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = false, Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

      controller.movePiece(gameState, piece, 0)
      piece.traveledFields shouldBe 0
      gameBoard.fields(0).piece shouldBe Some(piece)

      controller.movePiece(gameState, piece, 40)
      piece.traveledFields shouldBe 0
      gameBoard.fields(0).piece shouldBe Some(piece)
    }

    "handle edge cases for throwPlayerOut" in {
      val startHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false))
      val pieces = Array.tabulate(4)(i => Piece(Player("A", "Player1", Array(), Array(), startHouses, startPosition = 0), i + 1, traveledFields = 0, isInHome = false, isOnField = false, startHouses(i)))

      for (i <- pieces.indices) {
        startHouses(i).piece = Some(pieces(i))
        startHouses(i).isOccupied = true
      }

      val player1 = Player("A", "Player1", pieces, Array(), startHouses, startPosition = 0)
      val player2 = Player("B", "Player2", Array(), Array(), startHouses, startPosition = 0)

      val piece1Field = Field("00", 0, isOccupied = true, piece = None, isStartField = false, isHouseField = false)
      val piece1 = Piece(player1, 1, traveledFields = 0, isInHome = false, isOnField = true, piece1Field)
      piece1Field.piece = Some(piece1)

      val piece2Field = Field("00", 5, isOccupied = true, piece = None, isStartField = false, isHouseField = false)
      val piece2 = Piece(player2, 1, traveledFields = 5, isInHome = false, isOnField = true, piece2Field)
      piece2Field.piece = Some(piece2)

      val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      gameBoard.fields(0) = piece1Field
      gameBoard.fields(5) = piece2Field

      val gameState = GameState(List(player1, player2), player1, Dice(6), gameBoard, isRunning = true)

      controller.throwPlayerOut(player2, piece1, piece2Field, gameState)

      piece2.isOnField shouldBe false
      piece2.traveledFields shouldBe 0
      piece2.field shouldBe startHouses(0)
      startHouses(0).isOccupied shouldBe true
      startHouses(0).piece shouldBe Some(piece2)

      piece1.field shouldBe piece2Field
      piece1.traveledFields shouldBe 6
      piece2Field.piece shouldBe Some(piece1)
    }
  }
}