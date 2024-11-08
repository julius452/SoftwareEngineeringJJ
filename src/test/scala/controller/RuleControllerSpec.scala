package controller

import model.{Field, Dice, GameState, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach

class RuleControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  "A RuleController" should {
    val ruleController = new RuleController()
    val gameStateController = new GameStateController()

    val startField = Field("00", 0, isOccupied = true, piece = None, isStartField = false, isHouseField = false)
    val startHouses = Array.fill[Field](4)(startField)
    startHouses(0) = startField

    val player = new Player("A", "Player1", Array(), Array(), startHouses, startPosition = 0)
    val gameBoard = new GameBoardController().initializeGameBoard()
    val gameState = new GameState(List(player), player, Dice(3), gameBoard, isRunning = true)
    val piece = new Piece(player, 1, 0, isInHome = false, isOnField = true, gameBoard.fields(0))

    "detect collision on an occupied field" in {
      val landingField = gameBoard.fields(1)
      landingField.isOccupied = true
      landingField.piece = Some(new Piece(player, 2, 0, isInHome = false, isOnField = true, landingField))

      ruleController.checkCollision(piece, landingField, gameState) shouldBe true
    }

    "validate a move when the piece is not on the field and dice roll is 6" in {
      gameState.dice.lastRoll = 6
      ruleController.validateMove(piece, gameState) shouldBe true
    }

    "validate a move to an unoccupied field when piece is on the field" in {
      piece.isOnField = true
      gameState.dice.lastRoll = 4
      val landingIndex = (piece.field.position + gameState.dice.lastRoll) % gameState.board.fields.length
      val landingField = gameBoard.fields(landingIndex)
      landingField.isOccupied shouldBe false
      ruleController.validateMove(piece, gameState) shouldBe true
    }
  }
}