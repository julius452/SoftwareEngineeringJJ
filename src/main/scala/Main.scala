import model.{GameBoard, GameState, Piece, Player, Dice}
import controller.{GameController, PlayerController, RuleController}

object Main extends App {
  val gameController = new GameController()
  gameController.startNewGame()
}
