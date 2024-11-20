import controller.{GameController, PlayerController}
import model.{Dice, GameBoard, GameState, Player}
import view.{ConsoleView, GameInputHandler, PlayerInputHandler}

object Main extends App {
  val playerController = new PlayerController()
  val playerInputHandler = new PlayerInputHandler(playerController)

  val gameController = new GameController()
  val gameInputHandler = new GameInputHandler(gameController)

  val consoleView = new ConsoleView()

  // Init new Game
  // Get number of players
  var playerCountValid = false
  var playerCount = 0

  while (!playerCountValid) {
    println(consoleView.displayAskForPlayersCount())

    try{
      playerCount = scala.io.StdIn.readInt()

      playerCountValid = playerInputHandler.checkPlayerCountInput(playerCount)
    } catch{
      case _: NumberFormatException => playerCountValid = false
    }

    if (!playerCountValid) {
      println(consoleView.displayWrongInput())
    }
  }

  var players = List[Player]()
  // initialize players
  for (i <- 1 to playerCount) {
    println(consoleView.displayAskForPlayerName(i))
    val playerName = scala.io.StdIn.readLine()

    players = players :+ playerInputHandler.handlePlayerNameInput(playerName, i)
  }

  println(consoleView.displayDivider())

  // initialize gameState
  val gameDice = Dice()
  val gameBoard = GameBoard()
  gameBoard.initializeGameBoard()

  val gameState = GameState(players, gameDice, gameBoard)

  // determine StartingPlayer
  println(consoleView.displayDetermineStartingPlayer())

  var playersWithRolls = players.map { player =>
    print(consoleView.displayAskPlayerToRoll(player))

    while (!gameInputHandler.checkDiceRollInput(scala.io.StdIn.readLine())) {
      println(consoleView.displayWrongInput())
      print(consoleView.displayAskPlayerToRoll(player))
    }

    gameState.dice.rollDice()
    println(consoleView.displayDiceRollResult(player, gameState.dice.getLastRoll()))

    (player, gameState.dice.getLastRoll())
  }.toMap

  val startingPlayer = playerController.getStartingPlayer(playersWithRolls)
  gameState.updateCurrentPlayer(startingPlayer)

  println(consoleView.displayStartPlayer(startingPlayer))

  println(consoleView.displayDivider())

  //startGame();
  // run game
  while (gameState.getRunningState()) {
    val player = gameState.getCurrentPlayer()
    println(consoleView.displayTurnInfo(player))

    if (player.checkIfAllPiecesOffField()) {
      var rollCount = 0

      while (rollCount < 3) {
        print(consoleView.displayAskPlayerToRoll(player))

        while (!gameInputHandler.checkDiceRollInput(scala.io.StdIn.readLine())) {
          println(consoleView.displayWrongInput())
          print(consoleView.displayAskPlayerToRoll(player))
        }

        gameState.dice.rollDice()
        println(consoleView.displayDiceRollResult(player, gameState.dice.getLastRoll()))

        if (gameState.dice.getLastRoll() == 6) {
          // executePlayerTurn()
          gameController.executePlayerTurn()
        }

        rollCount += 1
      }
    } else {
      gameController.executePlayerTurn()
    }
  }
}
