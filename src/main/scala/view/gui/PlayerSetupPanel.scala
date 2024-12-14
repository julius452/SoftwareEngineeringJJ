package view.gui

import controller.ControllerInterface

import java.awt.{Color, Font, GraphicsEnvironment}
import java.io.File
import javax.swing.BorderFactory
import scala.runtime.BoxesRunTime.add
import scala.swing.Swing.HGlue
import scala.swing._
import scala.swing.event.{ButtonClicked, Key, KeyPressed}

class PlayerSetupPanel(controller: ControllerInterface) extends BorderPanel{
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  background = new Color(0, 100, 0)
  preferredSize = new Dimension(1000, 250)

  // Lade die Schriftart aus der .ttf Datei
  val headerFontFile = new File("src/main/resources/fonts/Birthstone-Regular.ttf")
  val customFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile)

  // Registriere die Schriftart im GraphicsEnvironment
  val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
  ge.registerFont(customFont)

  // Setze die Schriftart
  val myHeaderFont = customFont.deriveFont(Font.PLAIN, 60)
  val myFont = customFont.deriveFont(Font.PLAIN, 40)

  val currentPlayer: Int = controller.getCurrentPlayerSetUpNumber

  // Überschrift oben im Fenster
  val headerLabel = new FlowPanel() {
    contents += new Label("Spieler Benennen") {
      font = myHeaderFont
      foreground = Color.BLACK
    }
  }

  // Eingabefeld für Spielername
  val nameField = new TextField {
    font = myFont
    listenTo(keys)
    reactions += {
      case KeyPressed(_, Key.Enter, _, _) => controller.eval(text)
    }
  }

  Swing.onEDT {
    nameField.requestFocusInWindow()
  }

  // Panel für Eingabe
  val inputPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new Label(s"  Name für Spieler ${currentPlayer}:  ") {
      font = myFont
      foreground = Color.BLACK
    }
    contents += nameField
  }

  // Buttons unten im Fenster
  val leftButton: Button = new Button("< Zurück  ") {
    font = myFont
  }

  val rightButton: Button = new Button("Weiter  ") {
    font = myFont
  }

  val buttonPanel = new FlowPanel() {
    //contents += leftButton
    contents += rightButton
  }

  layout(headerLabel) = BorderPanel.Position.North
  layout(inputPanel) = BorderPanel.Position.Center
  layout(buttonPanel) = BorderPanel.Position.South

  listenTo(leftButton)
  listenTo(rightButton)

  reactions += {
    case ButtonClicked(`leftButton`) => controller.undo()
    case ButtonClicked(`rightButton`) => controller.eval(nameField.text)
  }
}
