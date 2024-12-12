package state

import builder.GameStateBuilder
import controller.MainController
import model.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PlayerSetupPhaseSpec extends AnyFlatSpec with Matchers {
  "evaluate should add player and move to next state when all players are added" should "add player and move to next state when input is valid" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.setPlayersCount(1)

    val controller = new MainController(gameState)
    val phase = PlayerSetupPhase(controller)
    phase.evaluate("Alice")

    gameState.getCreatedPlayers should be(1)
    gameState.getterPlayersList().head.getPlayerName() should be("Alice")
  }

  "evaluate should not add player on empty input" should "not add player when input is empty" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.setPlayersCount(1)

    val controller = new MainController(gameState)
    val phase = PlayerSetupPhase(controller)
    phase.evaluate("")

    gameState.getCreatedPlayers should be(0)
  }

  "evaluate should set first player and move to next state when all players are added" should "set first player and move to next state when all players are added" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    gameState.setPlayersCount(1)

    val controller = new MainController(gameState)
    val phase = PlayerSetupPhase(controller)
    phase.evaluate("Alice")

    gameState.getterPlayersList().head.getPlayerName() should be("Alice")
  }

  "getCurrentStateAsString should return the correct state string" should "return the player setup phase string" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val controller = new MainController(gameState)
    val phase = PlayerSetupPhase(controller)

    phase.getCurrentStateAsString should be(gameState.getPlayerSetupPhaseString)
  }
}