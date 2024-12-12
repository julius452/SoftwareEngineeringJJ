package view.gui

import controller.ControllerInterface

import javax.swing.BorderFactory
import scala.swing.Swing.HGlue
import scala.swing._
import scala.swing.event.{ButtonClicked, Key, KeyPressed}

class PlayerSetupPanel(controller: ControllerInterface) extends BoxPanel(Orientation.Vertical){
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  val myFont = new Font("Herculanum", java.awt.Font.PLAIN, 20)

  val currentPlayer: Int = controller.getCurrentPlayerSetUpNumber

  val nameTextBox: TextField = new TextField() {
    listenTo(keys)
    reactions += {
      case KeyPressed(_, Key.Enter, _, _) => controller.eval(text)
    }
  }

  val nextButton = new Button("\u2192")
  val prevButton = new Button("\u2190")

  listenTo(nextButton)
  listenTo(prevButton)

  contents += new FlowPanel() {
    contents += new Label("Player " + currentPlayer + ", please enter your name:") {
      font = myFont
    }
  }

  contents += nameTextBox
  contents += Swing.RigidBox(new Dimension(0, 20))

  contents += new BoxPanel(Orientation.Horizontal) {
    contents += prevButton
    contents += HGlue
    contents += nextButton
  }

  reactions += {
    case ButtonClicked(`nextButton`) => controller.eval(nameTextBox.text)
    case ButtonClicked(`prevButton`) => controller.undo()
  }
}
