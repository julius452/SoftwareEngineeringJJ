package model

import controller.RuleController
import memento.Caretaker
import view.ConsoleView
import view.ConsoleView.displayDivider

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

  def lastTurn(): Player = {
    val currentIndex = playersList.indexOf(currentPlayer)
    val lastPlayer = playersList((currentIndex - 1))

    lastPlayer
  }

  // ---

  private var playersCount: Int = 0
  private var rollCounter: Int = 0
  private var startingPlayerTracker: List[(Player, Int)] = List()
  private var createdPlayers: Int = 0
  private var triesToGetOutOfStartHouse: Int = 0
  private var isInExecutePlayerMove: Boolean = false
  private var showDiceResult: Boolean = true
  val careTaker = new Caretaker()

  def removePlayerFromList(player: Player): Unit = {
    val newList = playersList.filterNot(p => p == player)
    playersList = newList

    decreaseCreatedPlayers()
  }
  def getIsInExecutePlayerMove: Boolean = isInExecutePlayerMove
  def setIsInExecutePlayerMove(isInExecutePlayerMove: Boolean): Unit = {
    this.isInExecutePlayerMove = isInExecutePlayerMove
  }
  def getStartingPlayerTracker: List[(Player, Int)] = startingPlayerTracker
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

    careTaker.save(newPlayer)

    val newPlayerList = oldPlayerList ::: List(newPlayer)

    playersList = newPlayerList

    createdPlayers += 1
  }

  def getCreatedPlayers: Int = createdPlayers
  def decreaseCreatedPlayers(): Unit = {
    createdPlayers -= 1
  }

  def getFieldByPosition(position: Int): Field = {
    board.getFields()(position)
  }

  def getPlayerSetupPhaseString: String = {
    ConsoleView.displayPlayerSetupPhase(createdPlayers + 1)
  }

  def getDetermineStartPlayerPhaseString: String = {
    ConsoleView.displayDetermineStartPlayerPhase(this)
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
    val ruleController = new RuleController()

    val sb = new StringBuilder()

    if (showDiceResult) {
      sb.append(ConsoleView.displayDiceRoll(gameDice.getLastRoll(), getCurrentPlayer().getPlayerName()))
    }

    if (getTriesToGetOutOfStartHouse == 0) {
      sb.append(displayDivider())

      sb.append(s"${getCurrentPlayer().getPlayerName()} ist am Zug.\n")
      sb.append('\n')

      sb.append(ConsoleView.displayGameBoard(this))

      if (isInExecutePlayerMove) {
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
        showDiceResult = false
      } else{
        showDiceResult = true
        sb.append(ConsoleView.displayPleaseRoll(getCurrentPlayer()))
      }
    } else {
      sb.append("\nDu kannst noch nicht aus dem Haus ziehen!\n")
      sb.append("Du hast noch " + (3 - getTriesToGetOutOfStartHouse) + " Versuch(e)\n")
      sb.append("-------------------------\n")
      sb.append(ConsoleView.displayPleaseRoll(getCurrentPlayer()))
    }

    sb.toString()
  }

  def getValidMoves(): List[(Int,String)] = {
    val ruleController = new RuleController()

    // Überprüfe die Züge aller Spielfiguren des aktuellen Spielers
    currentPlayer.getPieces().zipWithIndex.collect {
      case (piece, index) if ruleController.validateMove(piece, this) =>
        (index, ConsoleView.displayValideMove(piece))
    }.toList
  }

  def getExecutePlayerTurnPhaseString: String = {
    val ruleController = new RuleController()
    val sb = new StringBuilder()


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

  def getCurrentPlayerNumber: Int = currentPlayer.getPlayerNumber()

  def getStartHouseByPlayerAndIndex(playerNumber: Int, index: Int): Field = {
    playersList(playerNumber - 1).getStartHouse()(index)
  }

  def getPlayerNameByPlayerNumber(playerNumber: Int): String = {
    playersList(playerNumber - 1).getPlayerName()
  }
}