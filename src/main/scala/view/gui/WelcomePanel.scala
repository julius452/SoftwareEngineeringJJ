package view.gui

import controller.ControllerInterface

import java.awt.{GraphicsEnvironment, Font}
import java.io.File
import javax.swing.BorderFactory
import scala.swing._
import scala.swing.event.ButtonClicked

class WelcomePanel (controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) {
  background = new Color(0, 100, 0)
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  val myFont = new Font("Herculanum", java.awt.Font.PLAIN, 20)

  // Lade die Schriftart aus der .ttf Datei
  val headerFontFile = new File("src/main/resources/fonts/Birthstone-Regular.ttf")
  val customFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile)

  // Registriere die Schriftart im GraphicsEnvironment
  val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
  ge.registerFont(customFont)

  // Setze die Schriftart
  val myHeaderFont = customFont.deriveFont(Font.PLAIN, 60)

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
    contents += new Label("Willkommen zu Mensch ärgere dich nicht!  ") {
      font = myHeaderFont
    }
  }

  contents += new FlowPanel() {
    contents += new Label("Wie viele Spieler wollen mitspielen?") {
      font = myFont
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
