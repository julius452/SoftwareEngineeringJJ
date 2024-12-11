package state

import controller.MainController

case class ExecutePlayerTurnPhase(controller: MainController) extends GamePhase {
  override def evaluate(input: String): Unit = {
    val number = MainController.toInt(input)

    if (number.isEmpty) return // invalid input

    controller.doStep(number.get)

    if (controller.gameState.gameDice.getLastRoll() != 6) {
      controller.gameState.nextTurn()
    }

    controller.nextState()
  }

  override def getCurrentStateAsString: String = controller.gameState.getExecutePlayerTurnPhaseString

  override def nextState: GamePhase = InGamePhase(controller)
}
