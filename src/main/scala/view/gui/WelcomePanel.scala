package view.gui

import controller.ControllerInterface

import javax.swing.BorderFactory
import scala.swing._
import scala.swing.event.ButtonClicked

class WelcomePanel (controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) {
  background = new Color(0, 100, 0)
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  val myFont = new Font("Herculanum", java.awt.Font.PLAIN, 20)
  val twoPlayerButton: Button = new Button("2 Players") {
    font = myFont
  }

  val threePlayerButton: Button = new Button("3 Players") {
    font = myFont
  }

  val fourPlayerButton: Button = new Button("4 Players") {
    font = myFont
  }

  contents += new FlowPanel() {
    contents += new Label {
      //private val temp = new ImageIcon("src/main/resources/wizard_logo.png").getImage
      //private val resize = temp.getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)
      //icon = new ImageIcon(resize)
    }
  }

  contents += new FlowPanel() {
    contents += new Label("How many Players?") {
      font = new Font("Herculanum", java.awt.Font.PLAIN, 40)
    }
  }

  contents += new FlowPanel() {
    contents += twoPlayerButton
    contents += threePlayerButton
    contents += fourPlayerButton
  }

  listenTo(twoPlayerButton)
  listenTo(threePlayerButton)
  listenTo(fourPlayerButton)


  reactions += {
    case ButtonClicked(`twoPlayerButton`) => controller.eval("2")
    case ButtonClicked(`threePlayerButton`) => controller.eval("3")
    case ButtonClicked(`fourPlayerButton`) => controller.eval("4")
  }
}
