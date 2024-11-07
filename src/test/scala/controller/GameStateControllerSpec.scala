package controller

import model.{Field, GameBoard, GameState, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach

class GameStateControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  "A GameStateController" should {
    val gameStateController = new GameStateController()
    val gameBoardController = new GameBoardController()
    val player = new Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
    val players = List(player)

    "initialize a new game state" in {
      val gameState = gameStateController.initializeGameState(players)
      gameState.players should contain(player)
      gameState.currentPlayer shouldBe player
      gameState.isRunning shouldBe true
    }

    "update the current player" in {
      val gameState = gameStateController.initializeGameState(players)
      val newPlayer = new Player("B", "Player2", Array(), Array(), Array(), startPosition = 10)
      gameStateController.updateCurrentPlayer(gameState, newPlayer)
      gameState.currentPlayer shouldBe newPlayer
    }

    "end the game by updating the running state" in {
      val gameState = gameStateController.initializeGameState(players)
      gameStateController.updateRunningState(gameState, isRunning = false)
      gameState.isRunning shouldBe false
    }
  }
}