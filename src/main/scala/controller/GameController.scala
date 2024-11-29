package controller

import model.{Dice, GameBoard, GameState, Player}
import builder.GameStateBuilder
import state.{GamePhase, MovePhase, RollingPhase}
import view.ConsoleView

class GameController {
  private val consoleView = new ConsoleView()
  private val playerController = new PlayerController()
  private val gameBoardController = new GameBoardController()
  private val ruleController = new RuleController()

  private var _gameState: GameState = _ //will be initialized in startNewGame

  def startNewGame(): Unit = {
    // Spieler initialisieren
    val players = playerController.initializePlayers()

    println(consoleView.displayDivider())

    // builder pattern
    _gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .buildPlayers(players)
      .build()

    // Observer hinzufügen
    _gameState.addObserver(consoleView)

    // Startspieler bestimmen
    val startingPlayer = determineStartingPlayer(players)

    println(consoleView.displayDivider())

    _gameState.updateCurrentPlayer(startingPlayer)

    // Spielzustand setzen
    _gameState.setPhase(new RollingPhase())

    startGame();
  }

  def determineStartingPlayer(players: List[Player]): Player = {
    println(consoleView.displayDetermineStartingPlayer())

    val playersWithRolls = players.map { player =>
      askToRollDice(player)
      (player, _gameState.dice.getLastRoll())
    }

    val startingPlayer = playersWithRolls.maxBy(_._2)._1;
    println(consoleView.displayStartPlayer(startingPlayer))

    return startingPlayer
  }

  def askToRollDice(player: Player): Unit = {
    print(consoleView.displayAskPlayerToRoll(player))

    while (scala.io.StdIn.readLine() != "w"){
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
        //askToRollDice(currentPlayer)
        //executePlayerTurn()
        // RollingPhase ausführen
        _gameState.setPhase(new RollingPhase())
        _gameState.executeCurrentPhase(this) // Spieler würfelt in der RollingPhase

        // MovePhase ausführen
        _gameState.setPhase(new MovePhase())
        _gameState.executeCurrentPhase(this) // Spieler bewegt eine Figur in der MovePhase
      }

      if (_gameState.dice.getLastRoll() != 6) {
        _gameState.nextTurn()
      } else {
        println(consoleView.displayPlayerCanRollAgain(currentPlayer))
      }

      println(consoleView.displayDivider())
    }
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

    // Bewegungsmöglichkeiten anzeigen
    val validMoves = currentPlayer.getPieces().zipWithIndex.collect {
      case (piece, index) if ruleController.validateMove(piece, _gameState) =>
        println(consoleView.displayValideMove(piece))
        index + 1
    }

    if (validMoves.isEmpty) {
      println("Fehler")
      return
    }

    println(consoleView.displayWhichPieceToMove())
    val input = scala.io.StdIn.readInt()

    val selectedPiece = currentPlayer.getPieces()(input - 1)

    // Strategie zum Bewegen der Figur anwenden
    ruleController.executeMove(selectedPiece, _gameState)

    println(consoleView.displayGameBoard(_gameState))
  }
}



