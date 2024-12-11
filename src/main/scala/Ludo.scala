import builder.GameStateBuilder
import controller.MainController
import view.TUI

import scala.io.StdIn.readLine

object Ludo extends App {
  val gameState = new GameStateBuilder()
    .buildDice()
    .buildGameBoard()
    .build()

  val controller: MainController = new MainController(gameState)

  val tui = new TUI(controller)
  //val gui = new SwingGui(controller)
  controller.notifyObservers()

  var input: String = ""

  while (true) {
    input = readLine()
    tui.processInput(input)
  }
}
