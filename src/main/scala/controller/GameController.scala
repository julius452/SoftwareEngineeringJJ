package controller

import model.{Dice, GameBoard, GameState, Piece, Player}
import builder.GameStateBuilder
import command.{CommandManager, MovePieceCommand}
import state.{GamePhase, MovePhase, RollingPhase}
import view.ConsoleView

import scala.util.{Failure, Success}

class GameController {
  private val consoleView = new ConsoleView()
  private val playerController = new PlayerController()
  private val gameBoardController = new GameBoardController()
  private val ruleController = new RuleController()
  private val commandManager = new CommandManager() // CommandManager einbinden
  private var _gameState: GameState = _ // Wird in startNewGame initialisiert



  def startNewGame(): Unit = {
    val players = playerController.initializePlayers()
    println(consoleView.displayDivider())

    _gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .buildPlayers(players)
      .build()

    _gameState.addObserver(consoleView)
    val startingPlayer = determineStartingPlayer(players)
    println(consoleView.displayDivider())

    _gameState.updateCurrentPlayer(startingPlayer)
    _gameState.setPhase(new RollingPhase())

    startGame()
  }

  def determineStartingPlayer(players: List[Player]): Player = {
    val playersWithRolls = players.map { player =>
      askToRollDice(player)
      (player, _gameState.dice.getLastRoll())
    }

    val startingPlayer = playersWithRolls.maxBy(_._2)._1
    println(consoleView.displayStartPlayer(startingPlayer))
    startingPlayer
  }

  def askToRollDice(player: Player): Unit = {
    print(consoleView.displayAskPlayerToRoll(player))

    while (scala.io.StdIn.readLine() != "w") {
      print(consoleView.displayWrongInput())
      print(consoleView.displayAskPlayerToRoll(player))
    }

    _gameState.dice.rollDice()
    println(consoleView.displayDiceRollResult(player, _gameState.dice.getLastRoll()))
  }

  def startGame(): Unit = {
    while (_gameState.getRunningState()) {
      val currentPlayer = _gameState.getCurrentPlayer()
      println(consoleView.displayTurnInfo(currentPlayer))

      if (currentPlayer.checkIfAllPiecesOffField()) {
        gameOpening(currentPlayer)
      } else {
        _gameState.setPhase(new RollingPhase())
        _gameState.executeCurrentPhase(this) // Spieler würfelt

        _gameState.setPhase(new MovePhase())
        _gameState.executeCurrentPhase(this) // Spieler bewegt eine Figur
      }

      println(consoleView.displayDivider())
      postMoveOptions() // Option für Undo/Redo nach jedem Zug
    }
  }

  def postMoveOptions(): Unit = {
    println("Möchten Sie den Zug rückgängig machen?")
    val input = scala.io.StdIn.readLine().toLowerCase

    input match {
      case "u" => undoStep()
      case _ => println("Ungültige Eingabe. Es wird der nächste Zug ausgeführt.")
    }
  }

  def undoStep(): Unit = {
    commandManager.undoStep()
    println(consoleView.displayGameBoard(_gameState))
  }

  def redoStep(): Unit = {
    commandManager.redoStep()
    println(consoleView.displayGameBoard(_gameState))
  }

  def gameOpening(player: Player): Unit = {
    var rollCount = 0

    while (rollCount < 3) {
      askToRollDice(player)

      if (_gameState.dice.getLastRoll() == 6) {
        executePlayerTurn()
        return
      }

      rollCount += 1
    }
  }

  def executePlayerTurn(): Unit = {
    println(consoleView.displayGameBoard(_gameState))

    val currentPlayer = _gameState.getCurrentPlayer()
    println(consoleView.displayPlayerCanEnterPiece(currentPlayer))

    val validMoves = currentPlayer.getPieces().zipWithIndex.collect {
      case (piece, index) if ruleController.validateMove(piece, _gameState) =>
        println(consoleView.displayValideMove(piece))
        index + 1
    }

    if (validMoves.isEmpty) {
      println("Keine gültigen Züge für den Spieler.")
      _gameState.nextTurn() // Spielerwechsel, wenn kein Zug möglich ist.
      return
    }

    println(consoleView.displayWhichPieceToMove())
    val input = scala.io.StdIn.readInt()

    val selectedPiece = currentPlayer.getPieces()(input - 1)
    ruleController.executeMove(selectedPiece, _gameState)

    // MoveCommand erstellen und ausführen
    val moveCommand = new MovePieceCommand(gameBoardController, _gameState, selectedPiece, _gameState.dice.getLastRoll())
    commandManager.doStep(moveCommand)

    println(consoleView.displayGameBoard(_gameState))


  }
}
