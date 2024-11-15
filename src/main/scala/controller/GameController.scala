package controller

import model.{Dice, GameBoard, GameState, Player}
import view.ConsoleView

class GameController() {
  private val consoleView = new ConsoleView()
  private val playerController = new PlayerController()
  private val gameBoardController = new GameBoardController()
  private val ruleController = new RuleController()

  private var _gameState: GameState = _ //will be initialized in startNewGame

  def startNewGame(): Unit = {
    // Spieler initialisieren
    val players = playerController.initializePlayers()

    println(consoleView.displayDivider())

    val gameDice = Dice()
    val gameBoard = GameBoard()
    gameBoard.initializeGameBoard()

    _gameState = GameState(players, gameDice, gameBoard)

    // Observer hinzufügen
    _gameState.addObserver(consoleView)

    // Startspieler bestimmen
    val startingPlayer = determineStartingPlayer(players)

    println(consoleView.displayDivider())

    _gameState.updateCurrentPlayer(startingPlayer)

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
        askToRollDice(currentPlayer)
        executePlayerTurn()
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

    // for (piece <- gameState.currentPlayer.pieces) {
    //  valdiateMove(piece, gameState)


    println(consoleView.displayPlayerCanEnterPiece(_gameState.getCurrentPlayer()))
    for (piece <- _gameState.getCurrentPlayer().getPieces()) {
      if (ruleController.validateMove(piece, _gameState)) {
        println(consoleView.displayValideMove(piece))
      }
    }

    println(consoleView.displayWhichPieceToMove())
    //println(consoleView.displayPlayerPossibleMoves(gameState.currentPlayer))

    val input = scala.io.StdIn.readInt()

    val pieceToRun = _gameState.getCurrentPlayer().getPieces()(input-1)

    if (!pieceToRun.getIsOnField()) {
      gameBoardController.movePiece(_gameState, _gameState.getCurrentPlayer().getPieces()(input-1), _gameState.dice.getLastRoll())
      println(consoleView.displayGameBoard(_gameState))
      return
    }

    val landingField = _gameState.board.getFields()((pieceToRun.getField().getPosition() + _gameState.dice.getLastRoll()) % _gameState.board.getFields().length)

    if (ruleController.checkCollision(pieceToRun, landingField, _gameState)) {
      gameBoardController.throwPlayerOut(landingField.getPiece().get.player, pieceToRun, landingField, _gameState)
      println(consoleView.displayGameBoard(_gameState))
      return
    }

    gameBoardController.movePiece(_gameState, pieceToRun, _gameState.dice.getLastRoll())
    //println(consoleView.displayGameBoard(_gameState))
  }
}



