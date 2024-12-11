package controller

import model.{Dice, Field, FieldType, GameBoard, GameState, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameBoardControllerSpec extends AnyWordSpec with Matchers {

  "GameBoardController" should {
    val controller = new GameBoardController

    "move a piece correctly" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()

      val piece = Piece(player, 1)
      piece.setIsOnField(true)

      val field = Field()
      field.inInitializeField(0, FieldType.GAME)
      field.setIsOccupied(true)

      piece.setField(field)
      val board = GameBoard()
      board.initializeGameBoard()

      val dice = Dice()
      val gameState = GameState(dice, board)

      controller.movePiece(gameState, piece, 5)
      piece.getTraveledFields() shouldBe 5
      board.getFields()(5).getPiece() shouldBe Some(piece)
    }

    "move a piece correctly out of startHouse" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()

      val piece = Piece(player, 1)
      piece.setIsOnField(false)

      val field = Field()
      field.inInitializeField(0, FieldType.STARTHOUSE)
      field.setIsOccupied(true)

      piece.setField(field)
      val board = GameBoard()
      board.initializeGameBoard()

      val dice = Dice()
      val gameState = GameState(dice, board)

      controller.movePiece(gameState, piece, 6)
      piece.getTraveledFields() shouldBe 0
      board.getFields()(0).getPiece() shouldBe Some(piece)
    }

    "move a piece correctly when traveling beyond field 39 into the player's house" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()

      val piece = Piece(player, 1)
      piece.setTravelFields(38)
      piece.setIsInHome(false)
      piece.setIsOnField(true)

      val field = Field()
      field.inInitializeField(38, FieldType.GAME)
      field.setIsOccupied(true)
      piece.setField(field)

      val gameBoard = GameBoard()
      gameBoard.initializeGameBoard()
      gameBoard.getFields()(38) = field

      val dice = Dice()
      val gameState = GameState(dice, gameBoard)

      controller.movePiece(gameState, piece, 3)

      piece.getIsInHome() shouldBe true
      piece.getIsOnField() shouldBe false
      piece.getField() shouldBe player.getHouse()(2)
      player.getHouse()(1).getPiece() shouldBe Some(piece)
      player.getHouse()(1).getIsOccupied() shouldBe true
      gameBoard.getFields()(38).getPiece() shouldBe None
      gameBoard.getFields()(38).getIsOccupied() shouldBe false
    }

    "throw a player out correctly" in {
      val player1 = Player(1, "Player1")
      player1.initializeHousesAndPieces()
      val player2 = Player(2, "Player2")
      player2.initializeHousesAndPieces()

      val piece1 = Piece(player1, 1)
      piece1.setIsOnField(true)
      val piece2 = Piece(player2, 1)
      piece2.setIsOnField(true)

      val field1 = Field()
      field1.inInitializeField(0, FieldType.GAME)
      field1.setIsOccupied(true)
      field1.setPiece(Some(piece1))
      piece1.setField(field1)

      val field2 = Field()
      field2.inInitializeField(5, FieldType.GAME)
      field2.setIsOccupied(true)
      field2.setPiece(Some(piece2))
      piece2.setField(field2)

      val gameBoard = GameBoard()
      gameBoard.initializeGameBoard()
      gameBoard.getFields()(0) = field1
      gameBoard.getFields()(5) = field2

      val dice = Dice()
      val gameState = GameState(dice, gameBoard)

      controller.throwPlayerOut(player1, piece1, field2, gameState)

      piece2.getIsOnField() shouldBe false
      piece2.getTraveledFields() shouldBe 0
      piece2.getField() shouldBe player2.getStartHouse()(0)
      player2.getStartHouse()(0).getIsOccupied() shouldBe true
      player2.getStartHouse()(0).getPiece() shouldBe Some(piece2)

      piece1.getField() shouldBe field2
      piece1.getTraveledFields() shouldBe dice.getLastRoll()
      field2.getPiece() shouldBe Some(piece1)
    }
  }
}