package controller

import model.GameState
import state.{GamePhase, StartPhase}

class MainController(var gameState: GameState) extends ControllerInterface {
  var state: GamePhase = StartPhase(this)

  def nextState(): Unit = state = state.nextState

  override def getCurrentStateAsString: String = state.getCurrentStateAsString

  override def eval(input: String): Unit = {
    state.evaluate(input)
    notifyObservers()
  }
}

object MainController{
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case _: Exception => None
    }
  }
}
