package view.gui

import controller.ControllerInterface

import scala.swing._
import java.awt.{Color, Dimension, Graphics}
import javax.swing.BorderFactory

class InGamePanel(controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) {

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

  def drawColor(row: Int, col:Int): Color = {
    // Home areas for each player (Red, Green, Blue, Yellow)
    if ((row >= 0 && row <= 1) && (col >= 0 && col <= 1)) {
      Color.YELLOW  // top left
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
    } else if (row == 0 && col == 6){
      Color.RED
    } else if (row == 6 && col == 10) {
      Color.BLUE
    } else if (row == 10 && col == 4) {
      Color.GREEN
    }
    // set home spaces
    else if(row == 5 && col >= 1 && col <= 4) {
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

  // Right half: Split into top and bottom panels
  val rightPanelTop = new BoxPanel(Orientation.Vertical) {
    background = Color.WHITE
    border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK), "Player Info")
  }

  val rightPanelBottom = new BoxPanel(Orientation.Vertical) {
    background = Color.GRAY
    border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK), "Game Controls")
  }

  val rightPanel = new BoxPanel(Orientation.Vertical) {
    contents += rightPanelTop
    contents += Swing.VStrut(10)
    contents += rightPanelBottom
  }

  // Layout setup
  contents += new SplitPane(Orientation.Horizontal, Component.wrap(gameBoardPanel.peer), Component.wrap(rightPanel.peer)) {
    dividerLocation = 400
  }
}
