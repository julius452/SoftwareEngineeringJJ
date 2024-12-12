package state

import builder.GameStateBuilder
import controller.MainController
import model.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExecutePlayerTurnPhaseSpec extends AnyFlatSpec with Matchers{
  "evaluate should do step and move to next state on valid input" should "do step and move to next state when input is a valid number" in {
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
    val phase = ExecutePlayerTurnPhase(controller)
    phase.evaluate("3")

    gameState.getCurrentPlayer() should be(player)
    gameState.gameDice.getLastRoll() should not be 6
  }

  "evaluate should not do step on invalid input" should "not do step when input is not a valid number" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = ExecutePlayerTurnPhase(controller)
    phase.evaluate("x")

    gameState.getCurrentPlayer() should be(null)
  }

  "evaluate should move to next turn if dice roll is not 6" should "move to next turn when dice roll is not 6" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player1 = Player(1, "Alice")
    player1.initializeHousesAndPieces()

    val player2 = Player(2, "Bob")
    player2.initializeHousesAndPieces()

    gameState.setPlayersCount(2)
    gameState.addPlayer("Alice")
    gameState.addPlayer("Bob")
    gameState.updateCurrentPlayer(player1)

    val controller = new MainController(gameState)
    val phase = ExecutePlayerTurnPhase(controller)
    phase.evaluate("3")

    gameState.getCurrentPlayer() should not be player1
  }

  "getCurrentStateAsString should return the correct state string" should "return the execute player turn phase string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = ExecutePlayerTurnPhase(controller)

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    phase.getCurrentStateAsString should be(gameState.getExecutePlayerTurnPhaseString)
  }
}
