package state

import controller.MainController
import view.ConsoleView

case class InGamePhase(controller: MainController) extends GamePhase {
  override def evaluate(input: String): Unit = {
    if (!input.equals("w")) return // invalid input
    // 3 mal würfeln
    controller.gameState.gameDice.rollDice()
    println(ConsoleView.displayDiceRoll(controller.gameState.gameDice.getLastRoll(), controller.gameState.getCurrentPlayer().getPlayerName()))

    if (controller.gameState.gameDice.getLastRoll() == 6 && controller.gameState.getCurrentPlayer().checkIfAllPiecesOffField()) {
      controller.gameState.resetTriesToGetOutOfStartHouse()
      controller.nextState()
    } else if (!controller.gameState.getCurrentPlayer().checkIfAllPiecesOffField()) {
      controller.nextState()
    } else if (controller.gameState.getTriesToGetOutOfStartHouse < 2) {
      controller.gameState.incrementTriesToGetOutOfStartHouse()
    } else {
      controller.gameState.resetTriesToGetOutOfStartHouse()
      controller.gameState.nextTurn()
    }
  }

  override def getCurrentStateAsString: String = controller.gameState.getInGamePhaseString

  override def nextState: GamePhase = ExecutePlayerTurnPhase(controller)
}
