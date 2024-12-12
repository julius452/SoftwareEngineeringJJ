package view.gui

import scala.swing._
import scala.swing.event._

class GameOverPanel(restartCallback: () => Unit) extends BorderPanel {
  val label = new Label("Spiel beendet! Möchtest du ein neues Spiel starten?")
  val restartButton = new Button("Neues Spiel starten")

  layout(label) = BorderPanel.Position.Center
  layout(restartButton) = BorderPanel.Position.South

  listenTo(restartButton)

  reactions += {
    case ButtonClicked(`restartButton`) => restartCallback()
  }
}
