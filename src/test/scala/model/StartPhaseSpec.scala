package state

import builder.GameStateBuilder
import controller.MainController
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import view.ConsoleView

class StartPhaseSpec extends AnyFlatSpec with Matchers {
  "evaluate should set players count and move to next state on valid input" should "set players count and move to next state when input is a valid number" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = StartPhase(controller)
    phase.evaluate("4")

    gameState.getterPlayersList().size should be(0)
  }

  "evaluate should not set players count on invalid input" should "not set players count when input is not a valid number" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = StartPhase(controller)
    phase.evaluate("x")

    gameState.getterPlayersList().size should be(0)
  }

  "evaluate should not set players count if number of players is invalid" should "not set players count when number of players is invalid" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = StartPhase(controller)
    phase.evaluate("0")

    gameState.getterPlayersList().size should be(0)
  }

  "getCurrentStateAsString should return the correct state string" should "return the start phase string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = StartPhase(controller)

    phase.getCurrentStateAsString should be(ConsoleView.displayStartPhase())
  }
}