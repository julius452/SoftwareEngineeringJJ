package state

import controller.MainController
import view.ConsoleView

case class StartPhase(controller: MainController) extends GamePhase {
  override def evaluate(input: String): Unit = {
    val number = MainController.toInt(input)

    if (number.isEmpty) return // invalid input

    if (!controller.gameState.checkNumberOfPlayers(number.get)) return

    controller.gameState.setPlayersCount(number.get)
    controller.nextState()
  }

  override def getCurrentStateAsString: String = ConsoleView.displayStartPhase()

  override def nextState: GamePhase = PlayerSetupPhase(controller)
}