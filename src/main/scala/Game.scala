import Controller.PlayboardController
import Model.PlayboardModel
import View.PlayboardView

object Game extends App{
  val model = new PlayboardModel()
  val controller = new PlayboardController(model, PlayboardView)

  // Starte das Spiel
  controller.printBoard()
}
