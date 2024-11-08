import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach
import controller._
import model._

class GameControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  val playerController = new PlayerController()
  val gameBoardController = new GameBoardController()
  val ruleController = new RuleController()
  val gameController = new GameController()
  val gameStateController = new GameStateController()

  "A GameController" should {

    "initialize players correctly" in {
      val players = gameController.initializePlayers()
      players.length should (be >= 2 and be <= 4)
    }

    "determine the starting player correctly" in {
      val players = List(playerController.initializePlayer(1, "Player1"), playerController.initializePlayer(2, "Player2"))
      val gameState = GameState(players, players.head, Dice(6), gameBoardController.initializeGameBoard(), isRunning = true)
      val startingPlayer = gameController.determineStartingPlayer(gameState, players)
      players should contain (startingPlayer)
    }

    "roll the dice correctly" in {
      val dice = new Dice(6)
      gameController.askToRollDice(GameState(List(), null, dice, null, isRunning = true), Player("A", "Player1", Array(), Array(), Array(), 0))
      dice.lastRoll should (be >= 1 and be <= 6)
    }

    "start the game correctly" in {
      val players = List(playerController.initializePlayer(1, "Player1"), playerController.initializePlayer(2, "Player2"))
      val gameState = gameStateController.initializeGameState(players)
      gameController.startGame(gameState)
      gameState.isRunning shouldBe true
    }

    "handle game opening correctly" in {
      val player = playerController.initializePlayer(1, "Player1")
      val gameState = GameState(List(player), player, Dice(6), gameBoardController.initializeGameBoard(), isRunning = true)
      gameController.gameOpening(gameState, player)
      gameState.currentPlayer shouldBe player
    }

    "execute player turn correctly" in {
      val player = playerController.initializePlayer(1, "Player1")
      val gameState = GameState(List(player), player, Dice(6), gameBoardController.initializeGameBoard(), isRunning = true)
      gameController.executePlayerTurn(gameState)
      gameState.currentPlayer shouldBe player
    }
  }
}