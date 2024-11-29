package builder

import model.{GameState, Player}

trait StateBuilder {
  def buildPlayers(players: List[Player]): GameStateBuilder
  def buildDice(): GameStateBuilder
  def buildGameBoard(): GameStateBuilder
  def build(): GameState
}
