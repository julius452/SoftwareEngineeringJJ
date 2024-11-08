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

  "A GameController" should {

    "initialize the game correctly" in {
      val players = List(playerController.initializePlayer(1, "Player1"), playerController.initializePlayer(2, "Player2"))
      val gameState = gameController.initializeGame(players)

      gameState.players.length shouldBe 2
      gameState.board.fields.length shouldBe 40
      gameState.isRunning shouldBe true
    }

    "roll the dice correctly" in {
      val dice = new Dice(6)
      gameController.rollDice(dice)
      dice.lastRoll should (be >= 1 and be <= 6)
    }

    "move a piece correctly" in {
      val player = playerController.initializePlayer(1, "Player1")
      val piece = player.pieces.head
      val gameBoard = gameBoardController.initializeGameBoard()
      val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

      gameController.movePiece(gameState, piece, 5)
      piece.traveledFields shouldBe 5
      gameBoard.fields(5).piece shouldBe Some(piece)
    }

    "handle collisions correctly" in {
      val player1 = playerController.initializePlayer(1, "Player1")
      val player2 = playerController.initializePlayer(2, "Player2")
      val piece1 = player1.pieces.head
      val piece2 = player2.pieces.head
      val gameBoard = gameBoardController.initializeGameBoard()
      val gameState = GameState(List(player1, player2), player1, Dice(6), gameBoard, isRunning = true)

      piece2.field = gameBoard.fields(5)
      gameBoard.fields(5).piece = Some(piece2)
      gameBoard.fields(5).isOccupied = true

      gameController.movePiece(gameState, piece1, 5)
      piece2.isInHome shouldBe true
      piece2.isOnField shouldBe false
      gameBoard.fields(5).piece shouldBe Some(piece1)
    }

    "check if a player can enter the goal correctly" in {
      val player = playerController.initializePlayer(1, "Player1")
      val piece = player.pieces.head
      val gameBoard = gameBoardController.initializeGameBoard()
      val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

      piece.traveledFields = 35
      gameController.canEnterGoal(piece, 5) shouldBe true
    }
  }
}