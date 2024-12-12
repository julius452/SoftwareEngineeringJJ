package view.gui

import scala.swing._
import scala.swing.event._
import javax.swing.{JFrame, SwingUtilities, JOptionPane}

class PlayerSetupPanel(playerCount: Int) {
  var playerNames = List[String]()

  // Methode zum Anzeigen eines Eingabefensters für einen Spieler
  private def showPlayerNameDialog(playerNumber: Int): String = {
    val input = JOptionPane.showInputDialog(
      null,
      s"Bitte Namen für Spieler $playerNumber eingeben:",
      s"Spieler $playerNumber",
      JOptionPane.PLAIN_MESSAGE
    )
    if (input == null || input.trim.isEmpty) "Unbekannt" else input.trim
  }

  // Methode zum Anzeigen des Bestätigungsdialogs
  private def showConfirmationDialog(): Boolean = {
    val response = JOptionPane.showConfirmDialog(
      null,
      "Bist du zufrieden mit den Spielernamen?",
      "Bestätigung",
      JOptionPane.YES_NO_OPTION
    )
    response == JOptionPane.YES_OPTION
  }

  // Hauptlogik zur Abfrage der Spielernamen
  def collectPlayerNames(): Unit = {
    playerNames = (1 to playerCount).map { i =>
      showPlayerNameDialog(i)
    }.toList

    println(s"Spieler Namen: $playerNames")

    if (showConfirmationDialog()) {
      println("Die Namen wurden bestätigt.")
    } else {
      println("Die Namen wurden nicht bestätigt. Bitte starte erneut.")
    }
  }

  // Starten des Prozesses in einem eigenen Fenster
  SwingUtilities.invokeLater(() => {
    collectPlayerNames()
  })
}
