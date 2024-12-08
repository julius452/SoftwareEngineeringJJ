package strategy

import controller.GameBoardController
import model.{GameState, Piece}

class CollisionMoveStrategy extends MoveStrategy {
  override def movePiece(gameBoardController: GameBoardController, gameState: GameState, piece: Piece, steps: Int): Unit = {
    val landingField = gameState.board.getFields()((piece.getField().getPosition() + steps) % gameState.board.getFields().length)
    gameBoardController.throwPlayerOut(landingField.getPiece().get.player, piece, landingField, gameState)
  }
}