package state

import controller.MainController
import view.ConsoleView

case class InGamePhase(controller: MainController) extends GamePhase {
  override def evaluate(input: String): Unit = {
    if (controller.gameState.getIsInExecutePlayerMove) { // case player is in execute player move
      val number = MainController.toInt(input)
      if (number.isEmpty) return // invalid input

      controller.doStep(number.get)

      if (controller.gameState.gameDice.getLastRoll() != 6) {
        controller.gameState.nextTurn()
      }

      controller.gameState.setIsInExecutePlayerMove(false)
    } else {
      if (!input.equals("w")) return // invalid

      controller.gameState.gameDice.rollDice()

      if (controller.gameState.gameDice.getLastRoll() == 6 && controller.gameState.getCurrentPlayer().checkIfAllPiecesOffField()) {
        controller.gameState.resetTriesToGetOutOfStartHouse()
        controller.gameState.setIsInExecutePlayerMove(true)
      } else if (!controller.gameState.getCurrentPlayer().checkIfAllPiecesOffField()) {
        controller.gameState.setIsInExecutePlayerMove(true)
      } else if (controller.gameState.getTriesToGetOutOfStartHouse < 2) {
        controller.gameState.setIsInExecutePlayerMove(false)

        controller.gameState.incrementTriesToGetOutOfStartHouse()
      } else {
        controller.gameState.setIsInExecutePlayerMove(false)

        controller.gameState.resetTriesToGetOutOfStartHouse()
        controller.gameState.nextTurn()
      }
    }
  }

  override def getCurrentStateAsString: String = controller.gameState.getInGamePhaseString

  override def nextState: GamePhase = GameOverPhase(controller)
}
