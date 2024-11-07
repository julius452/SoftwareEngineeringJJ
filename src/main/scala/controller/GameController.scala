package controller

import model.{GameState, Player}
import view.ConsoleView

class GameController() {
  private val consoleView = new ConsoleView()
  private val diceController = new DiceController()
  private val playerController = new PlayerController()
  private val gameStateController = new GameStateController()
  private val gameBoardController = new GameBoardController()
  private val ruleController = new RuleController()

  def startNewGame(): Unit = {
    // Spieler initialisieren
    val players = initializePlayers()

    println(consoleView.displayDivider())

    val gameState = gameStateController.initializeGameState(players)

    // Startspieler bestimmen
    val startingPlayer = determineStartingPlayer(gameState, players)

    println(consoleView.displayDivider())

    gameStateController.updateCurrentPlayer(gameState, startingPlayer)

    startGame(gameState);
  }

  private def initializePlayers(): List[Player] = {
    println(consoleView.displayAskForPlayersCount())

    var inputValid = false
    var playersCount = 0
    while (!inputValid) {
      playersCount = scala.io.StdIn.readInt()

      if (playersCount >= 2 && playersCount <= 4) {
        inputValid = true
      } else {
        println(consoleView.displayWrongInput())
        println(consoleView.displayAskForPlayersCount())
      }
    }

    var players = List[Player]()
    for (i <- 1 to playersCount) {
      println(consoleView.displayAskForPlayerName(i))
      val playerName = scala.io.StdIn.readLine()
      players = players :+ playerController.initializePlayer(i, playerName)
    }

    return players
  }

  private def determineStartingPlayer(gameState: GameState, players: List[Player]): Player = {
    println(consoleView.displayDetermineStartingPlayer())

    val playersWithRolls = players.map { player =>
      askToRollDice(gameState, player)
      (player, gameState.dice.lastRoll)
    }

    val startingPlayer = playersWithRolls.maxBy(_._2)._1;
    println(consoleView.displayStartPlayer(startingPlayer))

    return startingPlayer
  }

  private def askToRollDice(gameState: GameState, player: Player): Unit = {
    print(consoleView.displayAskPlayerToRoll(player))

    while (scala.io.StdIn.readLine() != "w"){
      print(consoleView.displayWrongInput())
      print(consoleView.displayAskPlayerToRoll(player))
    }

    diceController.rollDice(gameState.dice)

    println(consoleView.displayDiceRollResult(player, gameState.dice.lastRoll))
  }

  private def startGame(gameState: GameState): Unit = {
    while (gameState.isRunning) {
      val currentPlayer = gameState.currentPlayer

      println(consoleView.displayTurnInfo(currentPlayer))

      if (playerController.checkIfAllPiecesOffField(currentPlayer)) {
        gameOpening(gameState, currentPlayer)
      } else {
        askToRollDice(gameState, currentPlayer)
        executePlayerTurn(gameState)
      }

      if (gameState.dice.lastRoll != 6) {
        gameStateController.nextTurn(gameState)
      } else {
        println(consoleView.displayPlayerCanRollAgain(currentPlayer))
      }

      println(consoleView.displayDivider())
    }
  }

  private def gameOpening(gameState: GameState, player: Player): Unit = {
    var rollCount = 0

    while (rollCount < 3) {
      askToRollDice(gameState, player)

      if (gameState.dice.lastRoll == 6) {
        println(consoleView.displayPlayerCanEnterPiece(player))
        executePlayerTurn(gameState)
        return
      }

      rollCount += 1
    }
  }

  private def executePlayerTurn(gameState: GameState): Unit = {
    println(consoleView.displayGameBoard(gameState))

    // for (piece <- gameState.currentPlayer.pieces) {
    //  valdiateMove(piece, gameState)

    println(consoleView.displayPlayerCanEnterPiece(gameState.currentPlayer))

    for (piece <- gameState.currentPlayer.pieces) {
      if (ruleController.validateMove(piece, gameState)) {
        println(consoleView.displayValideMove(piece))
      }
    }

    println(consoleView.displayWhichPieceToMove())
    //println(consoleView.displayPlayerPossibleMoves(gameState.currentPlayer))

    val input = scala.io.StdIn.readInt()

    val pieceToRun = gameState.currentPlayer.pieces(input-1)

    if (!pieceToRun.isOnField) {
      gameBoardController.movePiece(gameState, gameState.currentPlayer.pieces(input-1), gameState.dice.lastRoll)
      println(consoleView.displayGameBoard(gameState))
      return
    }

    val landingField = gameState.board.fields((pieceToRun.field.position + gameState.dice.lastRoll) % gameState.board.fields.length)

    if (ruleController.checkCollision(pieceToRun, landingField, gameState)) {
      gameBoardController.throwPlayerOut(landingField.piece.get.player, pieceToRun, landingField, gameState)
      println(consoleView.displayGameBoard(gameState))
      return
    }

    gameBoardController.movePiece(gameState, pieceToRun, gameState.dice.lastRoll)
    println(consoleView.displayGameBoard(gameState))

    //TODO: Implement player turn logic
  }
}



