package strategy

import controller.GameBoardController
import model.{GameState, Piece}

trait MoveStrategy {
  def movePiece(gameBoardController: GameBoardController, gameState: GameState, piece: Piece, steps: Int): Unit
}