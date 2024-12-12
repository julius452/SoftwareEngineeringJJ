package model

import builder.GameStateBuilder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import view.ConsoleView
import view.ConsoleView.displayPleaseRoll

class GameStateSpec extends AnyFlatSpec with Matchers {

  "updateCurrentPlayer should set the current player" should "update the current player correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    gameState.updateCurrentPlayer(player)
    gameState.getCurrentPlayer() should be(player)
  }

  "nextTurn should update the current player to the next one in the list" should "cycle through players correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player1 = Player(1, "Alice")
    val player2 = Player(2, "Bob")
    gameState.setPlayersCount(2)
    gameState.addPlayer("Alice")
    gameState.addPlayer("Bob")
    gameState.updateCurrentPlayer(player1)
    gameState.nextTurn()
    gameState.getCurrentPlayer() should be(player2)
    gameState.nextTurn()
    gameState.getCurrentPlayer() should be(player1)
  }

  "incrementTriesToGetOutOfStartHouse should increase the counter" should "increment the counter correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.incrementTriesToGetOutOfStartHouse()
    gameState.getTriesToGetOutOfStartHouse should be(1)
  }

  "resetTriesToGetOutOfStartHouse should reset the counter" should "reset the counter to zero" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.incrementTriesToGetOutOfStartHouse()
    gameState.resetTriesToGetOutOfStartHouse()
    gameState.getTriesToGetOutOfStartHouse should be(0)
  }

  "trackStartingPlayer should add a new entry to the tracker" should "track the starting player correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    gameState.updateCurrentPlayer(player)
    gameState.trackStartingPlayer(6)
    gameState.getStartingPlayerTracker should contain((player, 6))
  }

  "setStartingPlayer should set the player with the highest roll as the current player" should "set the correct starting player" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player1 = Player(1, "Alice")
    val player2 = Player(2, "Bob")
    gameState.setPlayersCount(2)
    gameState.addPlayer("Alice")
    gameState.addPlayer("Bob")
    gameState.updateCurrentPlayer(player1)
    gameState.trackStartingPlayer(4)
    gameState.updateCurrentPlayer(player2)
    gameState.trackStartingPlayer(6)
    gameState.setStartingPlayer()
    gameState.getCurrentPlayer() should be(player2)
  }

  "updateRunningState should set the running state" should "update the running state correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.updateRunningState(false)
    gameState.getRunningState() should be(false)
  }

  "incrementRollCounter should increase the roll counter" should "increment the roll counter correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.incrementRollCounter()
    gameState.getRollCounter() should be(1)
  }

  "setFirstPlayer should set the first player in the list as the current player" should "set the correct first player" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player1 = Player(1, "Alice")
    val player2 = Player(2, "Bob")
    gameState.setPlayersCount(2)
    gameState.addPlayer("Alice")
    gameState.addPlayer("Bob")
    gameState.setFirstPlayer()
    gameState.getCurrentPlayer() should be(player1)
  }

  "addPlayer should add a new player to the players list" should "add the player correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.setPlayersCount(2)
    gameState.addPlayer("Alice")
    gameState.addPlayer("Bob")
    gameState.getterPlayersList().size should be(2)
  }

  "getPlayerSetupPhaseString should return the correct setup phase string" should "return the correct string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.getPlayerSetupPhaseString should be(ConsoleView.displayPlayerSetupPhase(1))
  }

  "getDetermineStartPlayerPhaseString should return the correct determine start player phase string" should "return the correct string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    gameState.updateCurrentPlayer(player)
    gameState.getDetermineStartPlayerPhaseString should be(ConsoleView.displayDetermineStartPlayerPhase(player))
  }

  "getInGamePhaseString should return the correct in-game phase string" should "return the correct string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    gameState.getExecutePlayerTurnPhaseString should include(ConsoleView.displaNoValidMoves())
  }

  "getExecutePlayerTurnPhaseString should return the correct execute player turn phase string" should "return the correct string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    gameState.getExecutePlayerTurnPhaseString should include(ConsoleView.displaNoValidMoves())
  }
}