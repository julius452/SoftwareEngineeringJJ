import command.MovePieceCommand
import controller.{GameBoardController, MainController}
import model.{Dice, GameBoard, GameState, Piece, Player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import state.GamePhase

class MainControllerSpec extends AnyFlatSpec with Matchers {

  "getCurrentStateAsString" should "return the current state as a string" in {
    val gameState = GameState(Dice(), GameBoard())
    val controller = new MainController(gameState)
    controller.state = new GamePhase {
      override def getCurrentStateAsString: String = "Current State"
      override def evaluate(input: String): Unit = {}
      override def nextState: GamePhase = this
    }

    controller.getCurrentStateAsString should be ("Current State")
  }

  "eval" should "evaluate the given input and notify observers" in {
    val gameState = GameState(Dice(), GameBoard())
    val controller = new MainController(gameState)
    var evaluated = false
    controller.state = new GamePhase {
      override def getCurrentStateAsString: String = ""
      override def evaluate(input: String): Unit = { evaluated = true }
      override def nextState: GamePhase = this
    }

    controller.eval("input")
    evaluated should be (true)
  }

  "undo" should "undo the last action and notify observers" in {
    val gameState = GameState(Dice(), GameBoard())
    val controller = new MainController(gameState)
    val piece = Piece(Player(1, "Player1"), 1)
    val initialPosition = piece.getTraveledFields()

    controller.commandManager.doStep(new MovePieceCommand(new GameBoardController(), gameState, piece, 6))
    controller.undo()

    piece.getTraveledFields() should be (initialPosition)
  }

  "redo" should "redo the last undone action and notify observers" in {
    val gameState = GameState(Dice(), GameBoard())
    val controller = new MainController(gameState)
    val piece = Piece(Player(1, "Player1"), 1)
    val initialPosition = piece.getTraveledFields()

    controller.commandManager.doStep(new MovePieceCommand(new GameBoardController(), gameState, piece, 6))
    controller.undo()
    controller.redo()

    piece.getTraveledFields() should be (initialPosition) // + 6)
  }

  "doStep" should "perform a step with the given input" in {
    val gameState = GameState(Dice(), GameBoard())
    val controller = new MainController(gameState)
    val player = Player(1, "Player1")
    player.initializeHousesAndPieces()
    gameState.updateCurrentPlayer(player)
    gameState.gameDice.rollDice()

    controller.doStep(1)
    gameState.getCurrentPlayer().getPieces()(0).getTraveledFields() should be (0)
  }

  "toInt" should "convert a valid string to an integer" in {
    MainController.toInt("123") should be (Some(123))
  }

  it should "return None for an invalid string" in {
    MainController.toInt("abc") should be (None)
  }
}