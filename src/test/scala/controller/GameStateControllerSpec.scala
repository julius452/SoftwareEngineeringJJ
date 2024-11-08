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

    "correctly switch to the next player when there are two players" in {
      // Setup
      val player1 = new Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val player2 = new Player("B", "Player2", Array(), Array(), Array(), startPosition = 10)
      val players = List(player1, player2)
      val gameState = gameStateController.initializeGameState(players)

      // Der aktuelle Spieler sollte player1 sein
      gameState.currentPlayer shouldBe player1

      // Wechsel zum nächsten Spieler
      gameStateController.nextTurn(gameState)
      gameState.currentPlayer shouldBe player2

      // Wechsel zurück zum ersten Spieler (player1)
      gameStateController.nextTurn(gameState)
      gameState.currentPlayer shouldBe player1
    }

    "end the game by updating the running state" in {
      val gameState = gameStateController.initializeGameState(players)
      gameStateController.updateRunningState(gameState, isRunning = false)
      gameState.isRunning shouldBe false
    }

    "update the running state of the game" in {
      val player1 = new Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val players = List(player1)
      val gameState = gameStateController.initializeGameState(players)

      // Zu Beginn sollte das Spiel laufen
      gameState.isRunning shouldBe true

      // Stoppe das Spiel
      gameStateController.updateRunningState(gameState, false)
      gameState.isRunning shouldBe false

      // Starte das Spiel wieder
      gameStateController.updateRunningState(gameState, true)
      gameState.isRunning shouldBe true
    }
  }
}