package state

import controller.MainController
import view.ConsoleView

case class DetermineStartPlayerPhase(controller: MainController) extends GamePhase{
  override def evaluate(input: String): Unit = {
    if (!input.equals("w")) return // invalid input

    controller.gameState.gameDice.rollDice()

    controller.gameState.incrementRollCounter()
    controller.gameState.trackStartingPlayer(controller.gameState.gameDice.getLastRoll())

    if (controller.gameState.getRollCounter() == controller.gameState.getPlayerCount) {
      controller.gameState.setStartingPlayer()
      controller.nextState()
      return
    }

    controller.gameState.nextTurn()
  }

  override def getCurrentStateAsString: String = controller.gameState.getDetermineStartPlayerPhaseString

  override def nextState: GamePhase = InGamePhase(controller)
}
