package view.gui


import controller.ControllerInterface
import sun.tools.jconsole.LabeledComponent.layout
import view.gui.WelcomePanel

import scala.swing._
import java.awt.{Color, Dimension, Graphics}
import javax.swing.{BorderFactory, JOptionPane}
import scala.swing.event.ButtonClicked


class InGamePanel(controller: ControllerInterface) extends BorderPanel {



  // Left half: Game board panel
  val gameBoardPanel = new Panel {
    preferredSize = new Dimension(400, 400)
    background = new Color(230, 230, 250) // Light purple

    // Method to draw the game board
    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)

      // Calculate the size of each cell (10x10 grid)
      val cellSize = size.width / 11

      // Draw the cells in a 10x10 grid
      for (row <- 0 until 11) {
        for (col <- 0 until 11) {
          val x = col * cellSize
          val y = row * cellSize

          // Set the color based on the position
          g.setColor(drawColor(row, col))

          // Draw the cell
          if (drawColor(row, col) != Color.LIGHT_GRAY) {
            g.fillRoundRect(x, y, cellSize - 5, cellSize - 5, 1000, 1000)
            g.setColor(Color.BLACK) // Border color
            g.drawRoundRect(x, y, cellSize - 5, cellSize - 5, 1000, 1000) // Cell border
          }


        }

      }

    }
  }




  def drawColor(row: Int, col: Int): Color = {
    // Home areas for each player (Red, Green, Blue, Yellow)
    if ((row >= 0 && row <= 1) && (col >= 0 && col <= 1)) {
      Color.YELLOW // top left
    } else if ((row >= 0 && row <= 1) && (col >= 9 && col <= 10)) {
      Color.RED // top right
    } else if ((row >= 9 && row <= 10) && (col >= 9 && col <= 10)) {
      Color.BLUE // bottom right
    } else if ((row >= 9 && row <= 10) && (col >= 0 && col <= 1)) {
      Color.GREEN // bottom left
    }
    // Set Start points
    else if (row == 4 && col == 0) {
      Color.YELLOW
    } else if (row == 0 && col == 6) {
      Color.RED
    } else if (row == 6 && col == 10) {
      Color.BLUE
    } else if (row == 10 && col == 4) {
      Color.GREEN
    }
    // set home spaces
    else if (row == 5 && col >= 1 && col <= 4) {
      Color.YELLOW
    } else if (row >= 1 && row <= 4 && col == 5) {
      Color.RED
    } else if (row == 5 && col >= 6 && col <= 9) {
      Color.BLUE
    } else if (row >= 6 && row <= 9 && col == 5) {
      Color.GREEN
    }
    // set game Path
    else if ((row == 4 && col >= 1 && col <= 4)
      || (row >= 0 && row <= 3 && col == 4)
      || (row == 0 && col == 5)
      || (row >= 1 && row <= 4 && col == 6)
      || (row == 4 && col >= 7 && col <= 10)
      || (row == 5 && col == 10)
      || (row == 6 && col >= 6 && col <= 9)
      || (row >= 7 && row <= 10 && col == 6)
      || (row == 10 && col == 5)
      || (row >= 6 && row <= 9 && col == 4)
      || (row == 6 && col >= 0 && col <= 3)
      || (row == 5 && col == 0)) {
      Color.WHITE
    } else {
      Color.LIGHT_GRAY // Rest of the spaces
    }
  }

  def getFieldFromID(id: Int): (Int, Int) = {
    // Home Bereiche (Gelb 0-3, Blau 4-7, Rot 8-11, Grün 12-15)
    if (id >= 0 && id <= 3) {
      return (id / 2, id % 2) // Gelb - ID 0-3
    } else if (id >= 8 && id <= 11) {
      return ((id - 8) / 2, (id - 8) % 2 + 9) // Rot - ID 8-11
    } else if (id >= 4 && id <= 7) {
      return ((id - 4) / 2 + 9, (id - 4) % 2 + 9) // Blau - ID 4-7
    } else if (id >= 12 && id <= 15) {
      return ((id - 12) / 2 + 9, (id - 12) % 2) // Grün - ID 12-15
    }

    // Set Startpunkte (Gelber Startpunkt: ID 16)
    else if (id == 16) {
      return (4, 0)
    } else if (id == 17) {
      return (0, 6)
    } else if (id == 18) {
      return (6, 10)
    } else if (id == 19) {
      return (10, 4)
    }

    // Setze die Felder entlang des Pfades nach dem Gelben Start
    else if (id >= 20 && id <= 23) {
      return (4, id - 20 + 1) // Gelbe Felder rechts
    } else if (id >= 24 && id <= 27) {
      return (id - 24, 4) // Aufwärts
    } else if (id == 27) {
      return (0, 5)
    } else if (id >= 28 && id <= 31) {
      return (id - 28 + 1, 6) // Rechts
    } else if (id >= 32 && id <= 35) {
      return (4, id - 32 + 7) // Rechts
    } else if (id == 36) {
      return (5, 10)
    } else if (id >= 37 && id <= 40) {
      return (6, id - 37 + 6) // Runter
    } else if (id >= 41 && id <= 44) {
      return (id - 41 + 7, 6) // Runter
    } else if (id == 45) {
      return (10, 5)
    } else if (id >= 46 && id <= 49) {
      return (6, id - 46 + 4) // Nach links
    } else if (id >= 50 && id <= 53) {
      return (6, id - 50) // Nach links
    } else if (id == 54) {
      return (5, 0)
    }

    // Alle anderen Felder sind leer oder nicht im Spielpfad
    else {
      return (-1, -1) // Ungültige ID
    }
  }

  def getFieldIndex(row: Int, col: Int): Int = {
    // Deine Logik, um den Index des Arrays auf die Position im 10x10-Gitter abzubilden
    if (row >= 1 && row <= 4 && col == 0) // Top left section
      return -1
    else if (row >= 1 && row <= 4 && col == 10) // Top right section
      return -1
    else if (row >= 6 && row <= 9 && col == 0) // Bottom left section
      return -1
    else if (row >= 6 && row <= 9 && col == 10) // Bottom right section
      return -1
    else if (row == 4 && col == 4) // Start for player 1
      return -1
    else if (row == 4 && col == 6) // Start for player 2
      return -1
    else if (row == 6 && col == 6) // Start for player 3
      return -1
    else if (row == 6 && col == 4) // Start for player 4
      return -1
    else {
      // Gib dir den Index in den mittleren Pfaden zurück
      // Diese Logik muss für den Spielpfad und jede andere relevante Position angepasst werden
      -1
    }
  }

  def setupGame(playerCount: Int): Unit = {
    // Erstelle ein Array, das die Spieler-IDs für jedes Feld speichert.
    // -1 bedeutet, dass das Feld leer ist
    val fieldContents = Array.fill(11, 11)(0) // 2D-Array für die Felder, initial auf -1 gesetzt

    // Fülle das Spielfeld basierend auf der Spieleranzahl
    playerCount match {
      case 2 =>
        // Gelb mit 1 und Blau mit 2 füllen
        for (i <- 0 to 3) {
          val (row, col) = getFieldFromID(i) // Berechne die Position basierend auf der ID
          fieldContents(row)(col) = 1  // Spieler 1 (Gelb)
        }
        for (i <- 4 to 7) {
          val (row, col) = getFieldFromID(i) // Berechne die Position basierend auf der ID
          fieldContents(row)(col) = 2  // Spieler 2 (Blau)
        }

      case 3 =>
        // Gelb mit 1, Blau mit 2 und Rot mit 3 füllen
        for (i <- 0 to 3) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 1  // Spieler 1 (Gelb)
        }
        for (i <- 4 to 7) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 2  // Spieler 2 (Blau)
        }
        for (i <- 8 to 11) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 3  // Spieler 3 (Rot)
        }

      case 4 =>
        // Gelb mit 1, Blau mit 2, Rot mit 3 und Grün mit 4 füllen
        for (i <- 0 to 3) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 1  // Spieler 1 (Gelb)
        }
        for (i <- 4 to 7) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 2  // Spieler 2 (Blau)
        }
        for (i <- 8 to 11) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 3  // Spieler 3 (Rot)
        }
        for (i <- 12 to 15) {
          val (row, col) = getFieldFromID(i)
          fieldContents(row)(col) = 4  // Spieler 4 (Grün)
        }
    }

  }


  def drawPlayer(g: Graphics, row: Int, col: Int, playerId: Int): Unit = {
    // Hier kannst du Spielfiguren basierend auf playerId zeichnen
    val cellSize = size.width / 11
    val x = col * cellSize + cellSize / 4
    val y = row * cellSize + cellSize / 4
    val playerSize = cellSize / 2

    // Set the color based on the playerId (example: 0 = Red, 1 = Green, etc.)
    val playerColor = playerId match {
      case 0 => Color.RED
      case 1 => Color.GREEN
      case 2 => Color.BLUE
      case 3 => Color.YELLOW
      case _ => Color.BLACK
    }

    // Draw the player piece
    g.setColor(playerColor)
    g.fillOval(x, y, playerSize, playerSize)
  }

  // Würfel Button
  val rollButton = new Button("Würfeln")


  // Right half: Split into top and bottom panels
  val rightPanelTop = new BoxPanel(Orientation.Vertical) {
    contents += rollButton
    background = Color.WHITE
    border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK), "Rolling Dice")

    listenTo(rollButton)
    reactions += {
      case ButtonClicked(`rollButton`) => {
        val currentPlayer = controller.getCurrentPlayerName
        controller.eval("w")
        val sb = new StringBuilder()

        sb.append(s"$currentPlayer hat eine ${controller.getLastRoll} gewürfelt!\n")


        JOptionPane.showMessageDialog(null, sb.toString(), "Würfel Ergebnis", JOptionPane.INFORMATION_MESSAGE)
      }}
    }

    val rightPanelBottom = new BoxPanel(Orientation.Vertical) {
      val validMoves = controller.getValidMoves
      contents += new Label(controller.getCurrentPlayerName + " ist am Zug")
      if (validMoves.isEmpty) {
        contents += new Label("Keine gültigen Züge verfügbar.")
      } else {
        validMoves.foreach { move =>
          // Erstelle einen Button mit dem Zug
          val moveButton = new Button(s"Zug: ${move}") {
            // Listener für Button-Klicks
            //reactions += {
             // case event: javax.swing.event.ActionEvent =>
          //      controller.performMove(move)  // Ausführen des Zugs
            //}
          }
          contents += moveButton
        }
        background = Color.WHITE
        border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK), "Player Info")
      }
    }

    val rightPanel = new BoxPanel(Orientation.Vertical) {
      contents += rightPanelTop
      contents += Swing.VStrut(10)
      contents += rightPanelBottom
    }



    layout(gameBoardPanel) = BorderPanel.Position.West
    layout(rightPanel) = BorderPanel.Position.East



  }
