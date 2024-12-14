package view.gui

import controller.ControllerInterface

import java.awt.{Color, FlowLayout, Font, GraphicsEnvironment, Image}
import java.io.File
import javax.swing.{BorderFactory, ImageIcon}
import scala.swing._
import scala.swing.event.ButtonClicked

class WelcomePanel (controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) {
  background = new Color(0, 100, 0)
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

  // Lade die Schriftart aus der .ttf Datei
  val headerFontFile = new File("src/main/resources/fonts/Birthstone-Regular.ttf")
  val customFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile)

  // Registriere die Schriftart im GraphicsEnvironment
  val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
  ge.registerFont(customFont)

  // Setze die Schriftart
  val myHeaderFont = customFont.deriveFont(Font.PLAIN, 60)
  val myFont = customFont.deriveFont(Font.PLAIN, 40)

  // Buttons für die Spieleranzahl
  val twoPlayerButton: Button = new Button("2 Players") {
    font = myFont
  }

  val threePlayerButton: Button = new Button("3 Players") {
    font = myFont
  }

  val fourPlayerButton: Button = new Button("4 Players") {
    font = myFont
  }

  val imagePath = "src/main/resources/images/maedn.png" // Bildpfad
  val imageIcon = new ImageIcon(imagePath)
  val scaledImage = imageIcon.getImage.getScaledInstance(380, 180, Image.SCALE_SMOOTH)
  val imageLabel = new Label {
    icon = new ImageIcon(scaledImage)
  }

    // Haupt-Panel mit BorderLayout, um die Überschrift oben zu platzieren
  val mainPanel = new BorderPanel {
    layout(new FlowPanel() {
      contents += new Label("Willkommen zu Mensch ärgere dich nicht!  ") {
        font = myHeaderFont
        foreground = Color.BLACK
      }
    }) = BorderPanel.Position.North

    layout(new FlowPanel() {
      contents += imageLabel
    }) = BorderPanel.Position.Center

    layout(new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel() {
        contents += new Label("Wie viele Spieler wollen mitspielen?  ") {
          font = myFont
          foreground = Color.BLACK
        }
      }

      contents += new FlowPanel() {
        contents += twoPlayerButton
        contents += threePlayerButton
        contents += fourPlayerButton
      }
    }) = BorderPanel.Position.South
  }

    contents += mainPanel

    listenTo(twoPlayerButton)
    listenTo(threePlayerButton)
    listenTo(fourPlayerButton)

    reactions += {
      case ButtonClicked(`twoPlayerButton`) => controller.eval("2")
      case ButtonClicked(`threePlayerButton`) => controller.eval("3")
      case ButtonClicked(`fourPlayerButton`) => controller.eval("4")
    }
}
