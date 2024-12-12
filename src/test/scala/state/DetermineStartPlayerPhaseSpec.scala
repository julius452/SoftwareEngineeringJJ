package state

import builder.GameStateBuilder
import controller.MainController
import model.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DetermineStartPlayerPhaseSpec extends AnyFlatSpec with Matchers  {
  "evaluate should roll dice and track starting player on valid input" should "roll dice and track starting player when input is 'w'" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    val controller = new MainController(gameState)
    val phase = DetermineStartPlayerPhase(controller)
    phase.evaluate("w")

    gameState.getRollCounter() should be(1)
    gameState.getStartingPlayerTracker should contain((player, gameState.gameDice.getLastRoll()))
  }

  "evaluate should not roll dice on invalid input" should "not roll dice when input is not 'w'" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = DetermineStartPlayerPhase(controller)
    phase.evaluate("x")

    gameState.getRollCounter() should be(0)
    gameState.getStartingPlayerTracker should be(empty)
  }

  "evaluate should set starting player and move to next state when all players have rolled" should "set starting player and move to next state when roll counter equals player count" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    val controller = new MainController(gameState)
    val phase = DetermineStartPlayerPhase(controller)
    phase.evaluate("w")

    gameState.getCurrentPlayer() should be(player)
  }

  "getCurrentStateAsString should return the correct state string" should "return the determine start player phase string" in {
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
    val phase = DetermineStartPlayerPhase(controller)

    phase.getCurrentStateAsString should be(gameState.getDetermineStartPlayerPhaseString)
  }
}
