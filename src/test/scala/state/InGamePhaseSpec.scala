package state

import builder.GameStateBuilder
import controller.MainController
import model.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InGamePhaseSpec extends AnyFlatSpec with Matchers {
  "evaluate should roll dice and move to next state on valid input" should "roll dice and move to next state when input is 'w'" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    val controller = new MainController(gameState)
    val phase = InGamePhase(controller)
    phase.evaluate("w")

    gameState.gameDice.getLastRoll() should be >= 1
    gameState.gameDice.getLastRoll() should be <= 6
  }

  "evaluate should not roll dice on invalid input" should "not roll dice when input is not 'w'" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = InGamePhase(controller)
    phase.evaluate("x")

    gameState.gameDice.getLastRoll() should be(0)
  }

  "evaluate should reset tries and move to next state if dice roll is 6 and all pieces are off field" should "reset tries and move to next state when dice roll is 6 and all pieces are off field" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    val player2 = Player(2, "Bob")
    player2.initializeHousesAndPieces()

    gameState.setPlayersCount(2)
    gameState.addPlayer("Alice")
    gameState.addPlayer("Bob")
    gameState.updateCurrentPlayer(player)

    val controller = new MainController(gameState)
    val phase = InGamePhase(controller)
    phase.evaluate("w")

    if (gameState.gameDice.getLastRoll() == 6) {
      gameState.getTriesToGetOutOfStartHouse should be(0)
    }else {
      gameState.getTriesToGetOutOfStartHouse should be(1)
    }
  }

  "evaluate should increment tries if dice roll is not 6 and pieces are not off field" should "increment tries when dice roll is not 6 and pieces are not off field" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    val controller = new MainController(gameState)
    val phase = InGamePhase(controller)
    gameState.gameDice.setLastRoll(3)
    phase.evaluate("w")

    if (gameState.gameDice.getLastRoll() == 6) {
      gameState.getTriesToGetOutOfStartHouse should be(0)
    }else {
      gameState.getTriesToGetOutOfStartHouse should be(1)
    }
  }

  "getCurrentStateAsString should return the correct state string" should "return the in game phase string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = InGamePhase(controller)

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    phase.getCurrentStateAsString should be(gameState.getInGamePhaseString)
  }
}