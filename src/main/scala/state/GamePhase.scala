package state

import controller.MainController

trait GamePhase {
  def evaluate(input: String): Unit

  def getCurrentStateAsString: String

  def nextState: GamePhase
}