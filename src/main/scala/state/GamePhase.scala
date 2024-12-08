package state

import controller.GameController
import model.GameState

trait GamePhase {
  def executePhase(gameController: GameController, gameState: GameState): Unit
}