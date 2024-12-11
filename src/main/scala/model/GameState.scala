package model

import command.{CommandManager, MovePieceCommand}
import controller.{GameBoardController, RuleController}
import view.ConsoleView

import scala.compiletime.uninitialized

case class GameState(gameDice: Dice, gameBoard: GameBoard) extends ModelInterface {
  private var playersList: List[Player] = List()

  private var currentPlayer: Player = uninitialized
  def dice: Dice = gameDice
  def board: GameBoard = gameBoard

  private var isRunning: Boolean = true

  def getterPlayersList(): List[Player] = playersList

  def updateCurrentPlayer(player: Player): Unit = {
    currentPlayer = player
  }

  def getCurrentPlayer(): Player = currentPlayer

  def updateRunningState(isRunning: Boolean): Unit = {
    this.isRunning = isRunning
  }

  def getRunningState(): Boolean = isRunning

  def nextTurn(): Unit = {
    val currentIndex = playersList.indexOf(currentPlayer)
    val nextPlayer = playersList((currentIndex + 1) % playersList.size)

    updateCurrentPlayer(nextPlayer)
  }

  // ---

  private var playersCount: Int = 0
  private var rollCounter: Int = 0
  private var startingPlayerTracker: List[(Player, Int)] = List()
  private var createdPlayers: Int = 0
  private var triesToGetOutOfStartHouse: Int = 0

  def getTriesToGetOutOfStartHouse: Int = triesToGetOutOfStartHouse
  def incrementTriesToGetOutOfStartHouse(): Unit = {
    triesToGetOutOfStartHouse += 1
  }

  def resetTriesToGetOutOfStartHouse(): Unit = {
    triesToGetOutOfStartHouse = 0
  }
  def getPlayerCount: Int = playersCount

  override def checkNumberOfPlayers(number: Int): Boolean = {
    Player.checkNumberOfPlayers(number)
  }

  override def setPlayersCount(numberOfPlayer: Int): Unit = {
    playersCount = numberOfPlayer
  }

  override def addPlayer(playerName: String): Unit = {
    val oldPlayerList = this.playersList

    val id = playersList.size + 1

    val newPlayer = Player(id, playerName)
    newPlayer.initializeHousesAndPieces()

    val newPlayerList = oldPlayerList ::: List(newPlayer)

    playersList = newPlayerList

    createdPlayers += 1
  }

  def getCreatedPlayers: Int = createdPlayers

  def getPlayerSetupPhaseString: String = {
    ConsoleView.displayPlayerSetupPhase(playersList.size + 1)
  }

  def getDetermineStartPlayerPhaseString: String = {
    ConsoleView.displayDetermineStartPlayerPhase(currentPlayer)
  }

  def setFirstPlayer(): Unit = {
    val startingPlayer = playersList.head
    updateCurrentPlayer(startingPlayer)
  }

  def incrementRollCounter(): Unit = {
    rollCounter += 1
  }

  def getRollCounter(): Int = {
    rollCounter
  }

  def trackStartingPlayer(roll: Int): Unit = {
    val oldList = this.startingPlayerTracker

    val newEntry = (currentPlayer, roll)

    val newList = oldList ::: List(newEntry)

    startingPlayerTracker = newList
  }

  def setStartingPlayer(): Unit = {
    val (startingPlayer, _) = startingPlayerTracker.maxBy(_._2)
    updateCurrentPlayer(startingPlayer)
  }

  def getInGamePhaseString: String = {
    ConsoleView.displayInGamePhaseString(this)
  }

  def getExecutePlayerTurnPhaseString: String = {
    val ruleController = new RuleController()
    val sb = new StringBuilder()

    sb.append(ConsoleView.displayGameBoard(this))

    sb.append(ConsoleView.displayPlayerCanEnterPiece())
    val validMoves = currentPlayer.getPieces().zipWithIndex.collect {
      case (piece, index) if ruleController.validateMove(piece, this) =>
        sb.append(ConsoleView.displayValideMove(piece))
        index + 1
    }

    if (validMoves.isEmpty) {
      sb.clear()
      sb.append(ConsoleView.displaNoValidMoves())
      return sb.toString()
    }

    sb.append(ConsoleView.displayWhichPieceToMove())
    sb.toString()
  }

  def movePiece(input: Int): Unit = {
    val gameBoardController = new GameBoardController()
    val commandManager = new CommandManager()

    val selectedPiece = currentPlayer.getPieces()(input - 1)

    // MoveCommand erstellen und ausführen
    val moveCommand = new MovePieceCommand(gameBoardController, this, selectedPiece, this.dice.getLastRoll())
    commandManager.doStep(moveCommand)
  }
}