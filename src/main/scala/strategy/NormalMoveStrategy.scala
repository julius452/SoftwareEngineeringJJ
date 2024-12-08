package strategy

import controller.GameBoardController
import model.{GameState, Piece}

class NormalMoveStrategy extends MoveStrategy {
  override def movePiece(gameBoardController: GameBoardController, gameState: GameState, piece: Piece, steps: Int): Unit = {
    gameBoardController.movePiece(gameState, piece, steps)
  }
}