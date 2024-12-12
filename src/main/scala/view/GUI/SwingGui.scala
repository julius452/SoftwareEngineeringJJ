package view.gui

import scala.swing._
import scala.swing.event._

object SwingGui extends SimpleSwingApplication {


  // Funktion, die das Hauptfenster zurückgibt
  def top: Frame = new MainFrame {
    title = "Mensch ärgere dich nicht"

    // Willkommens-Panel, auf dem die Spieleranzahl eingegeben wird
    var welcomePanel = new WelcomePanel()

    // Panel für das Setup der Spielernamen
    var playerSetupPanel: Option[PlayerSetupPanel] = None

    // Panel für das eigentliche Spiel
    var inGamePanel: Option[InGamePanel] = None

    // Zunächst das Willkommens-Panel anzeigen
    contents = welcomePanel

    // Wir beobachten das Spieleranzahl-Ereignis, das im Willkommens-Panel ausgelöst wird
    listenTo(welcomePanel.startButton)
    reactions += {
      case ButtonClicked(_) =>
        val playerCount = welcomePanel.playerCountField.text.toInt
        println(s"Spiel mit $playerCount Spielern wird gestartet.")

        // Wechsel zum Spieler-Setup-Panel
        playerSetupPanel = Some(new PlayerSetupPanel(playerCount))
        //contents = playerSetupPanel.get

        // Wir reagieren auf die Bestätigung des Spieler-Setups
        //listenTo(playerSetupPanel.get.doneButton)
        reactions += {
          case ButtonClicked(_) =>
            val playerNames = playerSetupPanel.get.playerNames
            println(s"Spieler Namen: $playerNames")

            // Wechsel zu InGamePanel (das eigentliche Spiel)
            inGamePanel = Some(new InGamePanel(playerNames))
            contents = inGamePanel.get
        }
    }

    // Setze eine bevorzugte Fenstergröße
    preferredSize = new java.awt.Dimension(800, 600)
  }
}
