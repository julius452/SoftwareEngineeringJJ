package state

import controller.GameController
import model.GameState

class RollingPhase extends GamePhase {
  override def executePhase(gameController: GameController, gameState: GameState): Unit = {
    gameController.askToRollDice(gameState.getCurrentPlayer())
  }
}