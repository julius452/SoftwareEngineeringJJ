package controller

import command.{CommandManager, MovePieceCommand}
import model.{Field, GameState, Player}
import state.{DetermineStartPlayerPhase, GameOverPhase, GamePhase, InGamePhase, PlayerSetupPhase, StartPhase}

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
    if (gameState.getCurrentPlayer() != null){
      val undoPlayerIndex = gameState.getCurrentPlayer().getPlayerNumber()
      val undoPlayer = gameState.getterPlayersList()(undoPlayerIndex - 1)
      gameState.careTaker.undo(undoPlayer)
    } else {
      gameState.careTaker.undo(new Player(1, ""))
    }

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

  def controllerStateAsString: String = {
    state match {
      case _: StartPhase => "StartPhase"
      case _: PlayerSetupPhase => "PlayerSetupPhase"
      case _: DetermineStartPlayerPhase => "DetermineStartPlayerPhase"
      case _: InGamePhase => "InGamePhase"
      case _: GameOverPhase => "GameOverPhase"
    }
  }

  override def getCurrentPlayerSetUpNumber: Int = gameState.getterPlayersList().size + 1
  override def getCurrentPlayerName: String = gameState.getCurrentPlayer().getPlayerName()
  override def getLastRoll: Int = gameState.gameDice.getLastRoll()
  override def getRollCounter: Int = gameState.getRollCounter()
  override def getPlayerCount: Int = gameState.getPlayerCount

  override def getValidMoves: List[(Int, String)] = gameState.getValidMoves()
  override def getIsExecutePlayerMove: Boolean = gameState.getIsInExecutePlayerMove
  override def getCurrentPlayerNumber: Int = gameState.getCurrentPlayer().getPlayerNumber()
  override def getFieldByPosition(position: Int): Field = gameState.getFieldByPosition(position)
  override def getPlayers(): List[Player] = gameState.getterPlayersList()
  override def getStartHouseByPlayerAndIndex(playerNumber: Int, index: Int): Field = gameState.getStartHouseByPlayerAndIndex(playerNumber, index)
  override def getLastPlayer: Player = gameState.lastTurn()
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
