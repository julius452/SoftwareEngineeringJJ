package view.gui

import scala.swing._
import scala.swing.event._
import java.awt.{Color, Graphics2D, Dimension}

class InGamePanel(playerNames: List[String]) extends BorderPanel {

  // Spielfeld-Komponente
  class GameBoard extends Panel {
    preferredSize = new Dimension(600, 600)

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)

      val tileSize = 40
      val boardSize = 11

      // Hintergrund
      g.setColor(Color.GRAY)
      g.fillRect(0, 0, size.width, size.height)

      // Farbliche Markierungen zuerst hinzufügen, damit das Grid danach immer sichtbar bleibt
      // Spielfeldraster

      // Farbliche Markierung für das Spielfeld-Kreuz
      val colors = List(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
      val startPositions = List(
        (0, 0), // Startbereich oben links
        (0, boardSize - 2), // Startbereich oben rechts
        (boardSize - 2, 0), // Startbereich unten links
        (boardSize - 2, boardSize - 2) // Startbereich unten rechts
      )

      // Startbereiche an den Ecken farblich markieren und mit 2x2 Feldern
      for ((color, (x, y)) <- colors.zip(startPositions)) {
        g.setColor(color)
        g.fillRect(x * tileSize, y * tileSize, tileSize * 2, tileSize * 2)
        g.setColor(Color.BLACK)
        g.drawLine(x * tileSize, y * tileSize + 1 * tileSize, x * tileSize + 2 * tileSize, y * tileSize + 1 * tileSize)
        g.drawLine(x * tileSize + 1 * tileSize, y * tileSize, x * tileSize + 1 * tileSize, y * tileSize + 2 * tileSize)
      }

      // Felder in den gewünschten Bereichen weiß färben (nur die Felder, die bereits weiß sind):

      // 1.-4. Zeile, 5.-7. Spalte (weiße Felder)
      for (i <- 4 to 6) { // Spalten 5 bis 7
        for (j <- 0 to 3) { // Zeilen 1 bis 4
          g.setColor(Color.WHITE)
          g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize)
        }
      }

      // 5.-7. Zeile, Alle Felder weiß
      for (i <- 4 to 6) { // Zeilen 5 bis 7
        for (j <- 0 until boardSize) { // Alle Spalten
          g.setColor(Color.WHITE)
          g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize)
        }
      }

      // 8.-11. Zeile, 5.-7. Spalte (weiße Felder)
      for (i <- 7 until boardSize) { // Zeilen 8 bis 11
        for (j <- 4 to 6) { // Spalten 5 bis 7
          g.setColor(Color.WHITE)
          g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize)
        }
      }

      // Nur die weißen Felder nachträglich einfärben
      // 1. Zeile, 7. Spalte - grün
      g.setColor(Color.GREEN)
      g.fillRect(6 * tileSize, 0 * tileSize, tileSize, tileSize)

      // 2.-4. Zeile, jeweils 6. Spalte - grün
      for (i <- 1 to 3) {
        g.setColor(Color.GREEN)
        g.fillRect(5 * tileSize, i * tileSize, tileSize, tileSize)
      }

      // 5. Zeile, 1. Spalte - rot
      g.setColor(Color.RED)
      g.fillRect(0 * tileSize, 4 * tileSize, tileSize, tileSize)

      // 5. Zeile, 6. Spalte - grün
      g.setColor(Color.GREEN)
      g.fillRect(5 * tileSize, 4 * tileSize, tileSize, tileSize)

      // 5. Zeile, 2.-5. Spalte - rot
      for (i <- 1 to 4) {
        g.setColor(Color.RED)
        g.fillRect(i * tileSize, 5 * tileSize, tileSize, tileSize)
      }

      // 5. Zeile, 7.-10. Spalte - gelb
      for (i <- 6 to 9) {
        g.setColor(Color.YELLOW)
        g.fillRect(i * tileSize, 5 * tileSize, tileSize, tileSize)
      }

      // 7. Zeile, 6. Spalte - blau
      g.setColor(Color.BLUE)
      g.fillRect(5 * tileSize, 6 * tileSize, tileSize, tileSize)

      // 7. Zeile, 11. Spalte - gelb
      g.setColor(Color.YELLOW)
      g.fillRect(10 * tileSize, 6 * tileSize, tileSize, tileSize)

      // 8.-10. Zeile, 6. Spalte - blau
      for (i <- 7 to 9) {
        g.setColor(Color.BLUE)
        g.fillRect(5 * tileSize, i * tileSize, tileSize, tileSize)
      }

      // 11. Zeile, 5. Spalte - blau
      g.setColor(Color.BLUE)
      g.fillRect(4 * tileSize, 10 * tileSize, tileSize, tileSize)

      // Nun das Spielfeldraster (Grids) aufzeichnen, damit es auf allen Feldern sichtbar ist
      g.setColor(Color.BLACK)
      for (i <- 0 until boardSize) {
        for (j <- 0 until boardSize) {
          g.drawRect(i * tileSize, j * tileSize, tileSize, tileSize)
        }
      }

    }
  }

  // Spielfeld hinzufügen
  layout(new GameBoard) = BorderPanel.Position.Center

  // Würfeln-Button auf Klassenebene definieren
  val rollButton = new Button("Würfeln")

  // Container für das Würfeln-Feld und die Spieler-Informationen
  val sidePanel = new BoxPanel(Orientation.Vertical) {
    contents += rollButton
    border = Swing.EmptyBorder(10, 10, 10, 10)

    // Spielerbereich
    val playerPanel = new BoxPanel(Orientation.Vertical) {
      playerNames.foreach(name => contents += new Label(name))
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }
    contents += playerPanel
  }

  // Layout für die rechte Seite (Würfeln und Spielerbereich)
  layout(sidePanel) = BorderPanel.Position.East

  listenTo(rollButton)
  reactions += {
    case ButtonClicked(`rollButton`) =>
      println("Würfeln wurde geklickt")
  }
}

// Hauptanwendung starten
object MenschAergereDichNichtApp extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "Mensch Ärgere Dich Nicht"
    contents = new InGamePanel(List("Spieler 1", "Spieler 2", "Spieler 3", "Spieler 4"))
    size = new Dimension(800, 800)
    centerOnScreen()
  }
}
