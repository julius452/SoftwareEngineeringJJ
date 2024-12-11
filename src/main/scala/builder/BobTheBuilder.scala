package builder

import model.{GameState, Player}

trait BobTheBuilder {
  def buildDice(): GameStateBuilder
  def buildGameBoard(): GameStateBuilder
  def build(): GameState
}
