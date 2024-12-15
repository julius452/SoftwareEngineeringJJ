package view.gui

import controller.ControllerInterface

import java.awt.{Color, Font, GradientPaint, GraphicsEnvironment}
import java.io.File
import javax.swing.{BorderFactory, JOptionPane}
import scala.swing._
import scala.swing.event.{ButtonClicked, Key, KeyPressed}

class DetermineStartPlayerPanel(controller: ControllerInterface) extends BorderPanel {
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  background = new Color(0, 100, 0)
  preferredSize = new Dimension(1000, 330)

  val headerFontFile = new File("src/main/resources/fonts/Birthstone-Regular.ttf")
  val customFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile)

  // Registriere die Schriftart im GraphicsEnvironment
  val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
  ge.registerFont(customFont)

  // Setze die Schriftart
  val myHeaderFont = customFont.deriveFont(Font.PLAIN, 60)
  val myFont = customFont.deriveFont(Font.PLAIN, 40)

  val headerLabel = new FlowPanel() {
    contents += new Label("Der Startspieler wird ermittelt") {
      font = myHeaderFont
      foreground = Color.BLACK
    }
  }

  val infoPanel = new FlowPanel() {
    contents += new Label(s"${controller.getCurrentPlayerName} ist an der Reihe, bitte würfeln  ") {
      font = myFont
      foreground = Color.BLACK
    }
  }

  val rollBtn = new Button("Würfeln") {
    preferredSize = new Dimension(150, 150)
    font = myFont
    foreground = Color.BLACK
  }

  val rollButton = new FlowPanel() {
    contents += rollBtn

    listenTo(keys)
    listenTo(rollBtn)

    reactions += {
      case KeyPressed(_, Key.Enter, _, _) => {
        controller.eval("w")
        val sb = new StringBuilder()

        sb.append(s"${controller.getLastPlayer.getPlayerName()} hat eine ${controller.getLastRoll} gewürfelt!\n")

        if (controller.getRollCounter == controller.getPlayerCount) {
          sb.append(s"${controller.getCurrentPlayerName} hat gewonnen und darf beginnen!")
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Würfel Ergebnis", JOptionPane.INFORMATION_MESSAGE)
      }
      case ButtonClicked(`rollBtn`) => {
        val lastPlayer = controller.getCurrentPlayerName
        controller.eval("w")
        val sb = new StringBuilder()

        sb.append(s"${lastPlayer} hat eine ${controller.getLastRoll} gewürfelt!\n")

        if (controller.getRollCounter == controller.getPlayerCount) {
          sb.append(s"${controller.getCurrentPlayerName} hat gewonnen und darf beginnen!")
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Würfel Ergebnis", JOptionPane.INFORMATION_MESSAGE)
      }
    }
  }

  layout(headerLabel) = BorderPanel.Position.North
  layout(infoPanel) = BorderPanel.Position.Center
  layout(rollButton) = BorderPanel.Position.South
}
