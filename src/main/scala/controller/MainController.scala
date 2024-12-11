package controller

import command.{CommandManager, MovePieceCommand}
import model.GameState
import state.{GamePhase, StartPhase}

class MainController(var gameState: GameState) extends ControllerInterface {
  var state: GamePhase = StartPhase(this)

  def nextState(): Unit = state = state.nextState
  val commandManager = new CommandManager

  override def getCurrentStateAsString: String = state.getCurrentStateAsString

  override def eval(input: String): Unit = {
    state.evaluate(input)
    notifyObservers()
  }

  override def undo(): Unit = {
    commandManager.undoStep()
    notifyObservers()
  }

  override def redo(): Unit = {
    commandManager.redoStep()
    notifyObservers()
  }

  override def doStep(input: Int): Unit = {
    val gameBoardController = new GameBoardController()
    val commandManager = new CommandManager()

    val selectedPiece = gameState.getCurrentPlayer().getPieces()(input - 1)

    // MoveCommand erstellen und ausführen
    val moveCommand = new MovePieceCommand(gameBoardController, gameState, selectedPiece, gameState.gameDice.getLastRoll())
    commandManager.doStep(moveCommand)
  }
}

object MainController{
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case _: Exception => None
    }
  }
}
