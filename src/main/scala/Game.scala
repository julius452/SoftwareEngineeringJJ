import controller.PlayboardController
import model.PlayboardModel
import view.PlayboardView

object Game extends App{
  val model = new PlayboardModel()
  val controller = new PlayboardController(model, PlayboardView)

  // Starte das Spiel
  print(controller.printBoard())
}
