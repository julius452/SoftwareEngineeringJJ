package state

import controller.MainController

case class PlayerSetupPhase(controller: MainController) extends GamePhase{
  override def evaluate(input: String): Unit = {
    if (input.isEmpty) return // invalid input

    controller.gameState.addPlayer(input)

    if (controller.gameState.getCreatedPlayers == controller.gameState.getPlayerCount) {
      controller.gameState.setFirstPlayer()
      controller.nextState()
    }
  }

  override def getCurrentStateAsString: String = controller.gameState.getPlayerSetupPhaseString

  override def nextState: GamePhase = DetermineStartPlayerPhase(controller)
}
