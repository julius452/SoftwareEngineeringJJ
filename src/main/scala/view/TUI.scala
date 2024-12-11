package view

import controller.ControllerInterface
import util.Observer

class TUI(controller: ControllerInterface) extends Observer {
  controller.add(this)

  override def update(): Unit = println(controller.getCurrentStateAsString)

  def processInput(input: String): Unit = {
    input match {
      case "u" => controller.undo()
      case "r" => controller.redo()
      case _ => controller.eval(input)
    }
  }
}
