import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.{GameState, Player, Dice, GameBoard}

class GameStateSpec extends AnyWordSpec with Matchers {

  "A GameState" should {

    "initialize with the correct list of players, dice, and game board" in {
      val player1 = Player(1, "Player1")
      val player2 = Player(2, "Player2")
      val playersList = List(player1, player2)
      val dice = new Dice()
      val gameBoard = new GameBoard()
      val gameState = GameState(dice, gameBoard)

      gameState.getterPlayersList() shouldBe playersList
      gameState.dice shouldBe dice
      gameState.board shouldBe gameBoard
    }

    "have the first player as the current player at the start" in {
      val player1 = Player(1, "Player1")
      val player2 = Player(2, "Player2")
      val playersList = List(player1, player2)
      val dice = new Dice()
      val gameBoard = new GameBoard()
      val gameState = GameState(dice, gameBoard)

      gameState.getCurrentPlayer() shouldBe player1
    }

    "update the current player correctly" in {
      val player1 = Player(1, "Player1")
      val player2 = Player(2, "Player2")
      val playersList = List(player1, player2)
      val dice = new Dice()
      val gameBoard = new GameBoard()
      val gameState = GameState(dice, gameBoard)

      gameState.updateCurrentPlayer(player2)
      gameState.getCurrentPlayer() shouldBe player2
    }

    "update the running state correctly" in {
      val player1 = Player(1, "Player1")
      val player2 = Player(2, "Player2")
      val playersList = List(player1, player2)
      val dice = new Dice()
      val gameBoard = new GameBoard()
      val gameState = GameState(dice, gameBoard)

      gameState.updateRunningState(false)
      gameState.getRunningState() shouldBe false

      gameState.updateRunningState(true)
      gameState.getRunningState() shouldBe true
    }

    "move to the next player correctly" in {
      val player1 = Player(1, "Player1")
      val player2 = Player(2, "Player2")
      val playersList = List(player1, player2)
      val dice = new Dice()
      val gameBoard = new GameBoard()
      val gameState = GameState(dice, gameBoard)

      gameState.nextTurn()
      gameState.getCurrentPlayer() shouldBe player2

      gameState.nextTurn()
      gameState.getCurrentPlayer() shouldBe player1
    }

    "handle the edge case when there is only one player" in {
      val player1 = Player(1, "Player1")
      val playersList = List(player1)
      val dice = new Dice()
      val gameBoard = new GameBoard()
      val gameState = GameState(dice, gameBoard)

      gameState.nextTurn()
      gameState.getCurrentPlayer() shouldBe player1 // Still the same player
    }
  }
}
