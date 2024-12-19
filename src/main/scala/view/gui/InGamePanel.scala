package view.gui


import controller.ControllerInterface
import model.Field
import sun.tools.jconsole.LabeledComponent
import sun.tools.jconsole.LabeledComponent.layout
import view.ConsoleView
import view.gui.WelcomePanel

import scala.swing.{FlowPanel, Label, _}
import java.awt.{Color, Dimension, Font, Graphics, GraphicsEnvironment}
import java.io.File
import javax.swing.{BorderFactory, JFrame, JOptionPane}
import scala.swing
import scala.swing.event.ButtonClicked


class InGamePanel(controller: ControllerInterface) extends BorderPanel {
  background = new Color(0, 100, 0)
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  preferredSize = new Dimension(1300, 710)

  // Lade die Schriftart aus der .ttf Datei
  val headerFontFile = new File("src/main/resources/fonts/Birthstone-Regular.ttf")
  val customFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile)

  // Registriere die Schriftart im GraphicsEnvironment
  val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
  ge.registerFont(customFont)

  // Setze die Schriftart
  val myHeaderFont = customFont.deriveFont(Font.PLAIN, 60)
  val myFont = customFont.deriveFont(Font.PLAIN, 30)
  val pieceText = new Font("Arial", Font.BOLD, 17)

  // Left half: Game board panel
  val gameBoardPanel = new Panel {
    preferredSize = new Dimension(700, 700)
    background = new Color(230, 230, 250) // Light purple
    border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

    // Method to draw the game board
    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)

      // Calculate the size of each cell (10x10 grid)
      val cellSize = size.width / 11

      g.setFont(pieceText)

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

            val fieldIndex = getFieldIndex(row, col)
            var fieldText = ""

            if (fieldIndex != -1) {
              val field = controller.getFieldByPosition(fieldIndex)
              if (field.getIsOccupied()) {
                drawPlayer(g, x, y, field.getPiece().get.player.getPlayerNumber())
                fieldText = s"${field.getPiece().get.player.getPlayerId() + field.getPiece().get.id}"
              }
            } else {
              val startHouseField = getStarthouse(row, col)

              if (startHouseField != null) {
                g.setColor(Color.darkGray)
                if (startHouseField.getIsOccupied()) {
                  drawPlayer(g, x, y, startHouseField.getPiece().get.player.getPlayerNumber())
                  fieldText = s"${startHouseField.getPiece().get.player.getPlayerId() + startHouseField.getPiece().get.id}"
                }
              }
            }

            val fontMetrics = g.getFontMetrics
            val textWidth = fontMetrics.stringWidth(fieldText)
            val textHeight = fontMetrics.getHeight
            val textX = x + (cellSize - textWidth) / 2
            var textY = y + (cellSize - textHeight) / 2 + fontMetrics.getAscent
            textY +=  5

            g.drawString(fieldText, textX, textY)
          } else {
            g.setColor(Color.BLACK)

            drawPlayerName(g, x, y, row, col)
          }
        }
      }
    }
  }

  def drawPlayerName(g: Graphics2D, x: Int, y: Int, row: Int, col: Int): Unit = {
    var fieldText = ""
    (row, col) match {
      case (2, 0) => fieldText = controller.getPlayerNameByPlayerNumber(1)
      case (2, 9) => fieldText = controller.getPlayerNameByPlayerNumber(2)
      case (8, 9) => fieldText = controller.getPlayerNameByPlayerNumber(3)
      case (8, 0) => fieldText = controller.getPlayerNameByPlayerNumber(4)
      case _ => return
    }

    val cellSize = size.width / 11

    val fontMetrics = g.getFontMetrics
    val textWidth = fontMetrics.stringWidth(fieldText)
    val textHeight = fontMetrics.getHeight
    val textX = x + (cellSize - textWidth) / 2
    val textY = y + (cellSize - textHeight) / 2 - 5

    g.drawString(fieldText, textX, textY)
  }

  val playerImages: Map[Int, Image] = Map(
    1 -> javax.imageio.ImageIO.read(new File("src/main/resources/images/figur-gelb.png")),
    2 -> javax.imageio.ImageIO.read(new File("src/main/resources/images/figur-rot.png")),
    3 -> javax.imageio.ImageIO.read(new File("src/main/resources/images/figur-blau.png")),
    4 -> javax.imageio.ImageIO.read(new File("src/main/resources/images/figur-grun.png"))
  )

  def getStarthouse(row: Int, col: Int): Field = {
    (controller.getPlayerCount, (row, col)) match {
      // Player 1
      case (2 | 3 | 4, (0, 0)) => controller.getStartHouseByPlayerAndIndex(1, 0)
      case (2 | 3 | 4, (0, 1)) => controller.getStartHouseByPlayerAndIndex(1, 1)
      case (2 | 3 | 4, (1, 0)) => controller.getStartHouseByPlayerAndIndex(1, 2)
      case (2 | 3 | 4, (1, 1)) => controller.getStartHouseByPlayerAndIndex(1, 3)

      // Player 2
      case (2 | 3 | 4, (0, 9)) => controller.getStartHouseByPlayerAndIndex(2, 0)
      case (2 | 3 | 4, (0, 10)) => controller.getStartHouseByPlayerAndIndex(2, 1)
      case (2 | 3 | 4, (1, 9)) => controller.getStartHouseByPlayerAndIndex(2, 2)
      case (2 | 3 | 4, (1, 10)) => controller.getStartHouseByPlayerAndIndex(2, 3)

      // Player 3
      case (3 | 4, (9, 9)) => controller.getStartHouseByPlayerAndIndex(3, 0)
      case (3 | 4, (9, 10)) => controller.getStartHouseByPlayerAndIndex(3, 1)
      case (3 | 4, (10, 9)) => controller.getStartHouseByPlayerAndIndex(3, 2)
      case (3 | 4, (10, 10)) => controller.getStartHouseByPlayerAndIndex(3, 3)

      // Player 4
      case (4, (9, 0)) => controller.getStartHouseByPlayerAndIndex(4, 0)
      case (4, (9, 1)) => controller.getStartHouseByPlayerAndIndex(4, 1)
      case (4, (10, 0)) => controller.getStartHouseByPlayerAndIndex(4, 2)
      case (4, (10, 1)) => controller.getStartHouseByPlayerAndIndex(4, 3)

      // Default case
      case _ => null
    }
  }

  def drawPlayer(g: Graphics, x: Int, y: Int, playerNumber: Int): Unit = {
    val cellSize = size.width / 11

    val playerSize = cellSize / 3

    val playerX = x + 10
    val playery = y + 10

    val playerImage = playerImages.get(playerNumber)
    playerImage.foreach { img =>
      g.drawImage(img, playerX, playery, playerSize, playerSize, null)
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

  def getFieldIndex(row: Int, col: Int): Int = {
    (row, col) match {
      case (4, 0) => 0
      case (4, 1) => 1
      case (4, 2) => 2
      case (4, 3) => 3
      case (4, 4) => 4

      case (3, 4) => 5
      case (2, 4) => 6
      case (1, 4) => 7
      case (0, 4) => 8

      case (0, 5) => 9
      case (0, 6) => 10

      case (1, 6) => 11
      case (2, 6) => 12
      case (3, 6) => 13
      case (4, 6) => 14

      case (4, 7) => 15
      case (4, 8) => 16
      case (4, 9) => 17
      case (4, 10) => 18

      case (5, 10) => 19
      case (6, 10) => 20

      case (6, 9) => 21
      case (6, 8) => 22
      case (6, 7) => 23
      case (6, 6) => 24

      case (7, 6) => 25
      case (8, 6) => 26
      case (9, 6) => 27
      case (10, 6) => 28

      case (10, 5) => 29
      case (10, 4) => 30

      case (9, 4) => 31
      case (8, 4) => 32
      case (7, 4) => 33
      case (6, 4) => 34

      case (6, 3) => 35
      case (6, 2) => 36
      case (6, 1) => 37
      case (6, 0) => 38

      case (5, 0) => 39

      case _ => -1
    }
  }

  def hasPiecesInStarthouse(playerNumber: Int): Boolean = {
    (0 to 3).exists { index =>
      val startHouseField = controller.getStartHouseByPlayerAndIndex(playerNumber, index)
      startHouseField.getIsOccupied()
    }
  }



  // Würfel Button
  val rollButton = new Button("Würfeln") {
    font = myFont
    preferredSize = new Dimension(100, 100)
  }

  val currentRoll =new FlowPanel(){
    contents += new Label(ConsoleView.displayDiceRoll(controller.getLastRoll, controller.getCurrentPlayerName)) {
      font = myFont
    }
  }

  // Right half: Split into top and bottom panels
  val rightPanelTop = new FlowPanel() {
    contents += rollButton
    contents += currentRoll

    listenTo(rollButton)
    reactions += {
      case ButtonClicked(`rollButton`) => {
        controller.eval("w")

      }
    }
  }

    val rightPanelBottom = new BoxPanel(Orientation.Vertical) {
      border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

      var counter = 0
      val validMoves = controller.getValidMoves

      val playerInfoLabel = new BoxPanel(Orientation.Vertical){
        contents += new Label(controller.getCurrentPlayerName + " ist am Zug") {
          font = myFont
        }
      }

      val playerInfo = new BoxPanel(Orientation.Vertical) {
        if (!controller.getIsExecutePlayerMove) {
          contents += new Label("Bitte zuerst würfeln!") {
            font = myFont
          }
        } else {
          if (validMoves.isEmpty) {
            contents += new Label("Keine gültigen Züge verfügbar.") {
              font = myFont
            }
          } else {
            validMoves.foreach { move =>
              val output = s"${move._2}"
              if (counter == 0) {
                val buttonMove1 = new Button(output) {
                  font = myFont
                }
                contents += buttonMove1
                listenTo(buttonMove1)
                reactions += {
                  case ButtonClicked(`buttonMove1`) => {
                    controller.eval("1")
                  }
                }
              } else if (counter == 1) {
                val buttonMove2 = new Button(output) {
                  font = myFont
                }
                contents += buttonMove2
                listenTo(buttonMove2)
                reactions += {
                  case ButtonClicked(`buttonMove2`) => {
                    controller.eval("2")
                  }
                }
              } else if (counter == 2) {
                val buttonMove3 = new Button(output) {
                  font = myFont
                }
                contents += buttonMove3
                listenTo(buttonMove3)
                reactions += {
                  case ButtonClicked(`buttonMove3`) => {
                    controller.eval("3")
                  }
                }
              } else if (counter == 3) {
                val buttonMove4 = new Button(output) {
                  font = myFont
                }
                contents += buttonMove4
                listenTo(buttonMove4)
                reactions += {
                  case ButtonClicked(`buttonMove4`) => {
                    controller.eval("4")
                  }
                }
              }
              counter += 1
            }
          }
        }
      }

      contents += playerInfoLabel
      contents += playerInfo
    }

    val rightPanel = new  BorderPanel() {
      preferredSize = new Dimension(560, 710)

      layout(rightPanelTop) = BorderPanel.Position.North
      //contents += Swing.VStrut(10)
      layout(rightPanelBottom) = BorderPanel.Position.Center
    }

    layout(gameBoardPanel) = BorderPanel.Position.West
    layout(rightPanel) = BorderPanel.Position.East
}
