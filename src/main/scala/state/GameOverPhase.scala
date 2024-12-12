package state

import controller.MainController

case class GameOverPhase(controller: MainController) extends GamePhase {
  override def evaluate(input: String): Unit = {
    // do nothing
  }

  override def getCurrentStateAsString: String = "Game Over"

  override def nextState: GamePhase = this
}
