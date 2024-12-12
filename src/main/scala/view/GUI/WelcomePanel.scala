package view.gui

import javax.swing.{JFrame, SwingUtilities}
import scala.swing._
import scala.swing.event._

class WelcomePanel extends BoxPanel(Orientation.Vertical) {
  val playerCountField = new TextField { columns = 10 }
  val startButton = new Button("Spiel starten")

  // Label und Textfeld zur Eingabe der Spieleranzahl
  contents += new Label {
    text = "Willkommen zu Mensch ärgere dich nicht!"
    font = new Font("Arial", java.awt.Font.BOLD, 24)
  }
  contents += new Label("Gib die Anzahl der Spieler ein:")
  contents += playerCountField
  contents += startButton

  // Actionlistener für den Button
  listenTo(startButton)
  reactions += {
    case ButtonClicked(_) =>
      val playerCount = playerCountField.text.toInt
      println(s"Spiel mit $playerCount Spielern wird gestartet.")
  }

  // Layout-Optionen
  border = Swing.EmptyBorder(10, 10, 10, 10)

  visible = true
  SwingUtilities.invokeLater(() => {
    val frame = new JFrame()
    frame.setContentPane(peer)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  })
}
