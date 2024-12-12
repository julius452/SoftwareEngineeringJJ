package view.gui

import controller.ControllerInterface

import java.awt.{Color, GradientPaint}
import javax.swing.BorderFactory
import scala.swing._
import scala.swing.event.ButtonClicked

class DetermineStartPlayerPanel(controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) {
  background = new Color(240, 240, 240) // Light gray
  border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
  preferredSize = new Dimension(450, 250)

  // Title
  contents += new Label("Decide Start Player") {
    font = new Font("Arial", java.awt.Font.BOLD, 24)
    foreground = new Color(30, 144, 255) // Dodger Blue
    horizontalAlignment = Alignment.Center
  }
  contents += Swing.VStrut(20)

  // Current player label
  contents += new Label(s"It's ${controller.getCurrentPlayerName}'s turn to roll the dice.") {
    font = new Font("Arial", java.awt.Font.PLAIN, 18)
    foreground = new Color(60, 60, 60) // Dark Gray
    horizontalAlignment = Alignment.Center
  }
  contents += Swing.VStrut(20)

  // Roll button
  val rollButton = new Button("Roll Dice") {
    font = new Font("Arial", java.awt.Font.PLAIN, 16)
    background = new Color(173, 216, 230) // Light Blue
    foreground = Color.WHITE
    opaque = true
    focusPainted = false
    border = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(30, 144, 255), 2),
      BorderFactory.createEmptyBorder(5, 15, 5, 15)
    )
    peer.setContentAreaFilled(false)
    peer.setOpaque(true)
  }

  contents += new FlowPanel(FlowPanel.Alignment.Center)(rollButton)

  // Custom background gradient
  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    val gradient = new GradientPaint(0f, 0f, new Color(240, 248, 255), size.getWidth.toFloat, size.getHeight.toFloat, new Color(173, 216, 230))
    g.setPaint(gradient)
    g.fillRect(0, 0, size.width, size.height)
  }

  // Listen for button click
  listenTo(rollButton)
  reactions += {
    case ButtonClicked(`rollButton`) => controller.eval("w")
  }
}
