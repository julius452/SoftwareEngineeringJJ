package view.gui

import controller.ControllerInterface
import util.Observer

import scala.swing._

class SwingGui(controller: ControllerInterface) extends Frame with Observer{
  controller.add(this)

  title = "Wizard"

  contents = new WelcomePanel(controller)

  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  visible = true
  centerOnScreen()
  resizable = false
  pack()

  override def update(): Unit = {
    contents = SwingGui.getPanel(controller)

    repaint()
  }

  override def isUpdated: Boolean = false
}

object SwingGui {
  def getPanel(controller: ControllerInterface): Panel = {
    controller.controllerStateAsString match {
      case "StartPhase" => new WelcomePanel(controller)
      case "PlayerSetupPhase" => new PlayerSetupPanel(controller)
      case "DetermineStartPlayerPhase" => new DetermineStartPlayerPanel(controller)
      case "InGamePhase" => new InGamePanel(controller)
      case "ExecutePlayerTurnPhase" => new ExecutePlayerTurnPanel(controller)
    }
  }
}
