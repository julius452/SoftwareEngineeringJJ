package state

import controller.GameController
import model.GameState

class StartPhase extends GamePhase {
  override def executePhase(gameController: GameController, gameState: GameState): Unit = {
    gameController.startNewGame()
  }
}