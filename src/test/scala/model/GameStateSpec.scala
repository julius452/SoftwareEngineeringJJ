package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameStateSpec extends AnyWordSpec with Matchers {

  "A GameState" should {
    val player1 = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
    val player2 = Player("B", "Player2", Array(), Array(), Array(), startPosition = 10)
    val dice = Dice(6)
    val fields = Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
    val board = GameBoard(fields)

    "initialize with correct attributes" in {
      val gameState = GameState(List(player1, player2), player1, dice, board, isRunning = true)

      gameState.players should contain allOf (player1, player2)
      gameState.currentPlayer shouldBe player1
      gameState.dice shouldBe dice
      gameState.board shouldBe board
      gameState.isRunning shouldBe true
    }

    "update current player and game status" in {
      val gameState = GameState(List(player1, player2), player1, dice, board, isRunning = true)

      gameState.currentPlayer = player2
      gameState.currentPlayer shouldBe player2

      gameState.isRunning = false
      gameState.isRunning shouldBe false
    }
  }
}