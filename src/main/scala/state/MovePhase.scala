package state

import controller.GameController
import model.GameState

class MovePhase extends GamePhase {
  override def executePhase(gameController: GameController, gameState: GameState): Unit = {
    gameController.executePlayerTurn()
  }
}