import builder.GameStateBuilder
import model.{Dice, GameBoard, GameState}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.compiletime.uninitialized

class GameStateBuilderSpec extends AnyFlatSpec with Matchers {

  "buildDice" should "initialize the game dice" in {
    val builder = new GameStateBuilder()
    builder.buildDice()
    val gameState = builder.build()
    gameState.gameDice should not be null
  }

  "buildGameBoard" should "initialize the game board" in {
    val builder = new GameStateBuilder()
    builder.buildGameBoard()
    val gameState = builder.build()
    gameState.gameBoard should not be null
  }

  "build" should "create a GameState with initialized dice and game board" in {
    val builder = new GameStateBuilder()
    builder.buildDice().buildGameBoard()
    val gameState = builder.build()
    gameState.gameDice should not be null
    gameState.gameBoard should not be null
  }

  "build" should "throw an exception if dice is not initialized" in {
    val builder = new GameStateBuilder()
    builder.buildGameBoard()
    noException should be thrownBy builder.build()
  }

  "build" should "throw an exception if game board is not initialized" in {
    val builder = new GameStateBuilder()
    builder.buildDice()
    noException should be thrownBy builder.build()
  }
}