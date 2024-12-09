package view

import controller.Controller
import util.Observable

class TUI(controller: Controller) extends Observable {
  def update(): Unit = {
    print(controller.gameStateToString())
  }

  def processInput(input: String): Unit = {
    input match {
      case "w" =>
      case _ => controller.processInput(input)
    }
  }
}
