package controller

import model.{Piece, Player}

class PieceController {
  def initializePiece(player: Player, id: Int): Piece = {
    val position = -1
    val isInHome, isOnField = false

    val piece = Piece(
      player,
      id,
      position,
      isInHome,
      isOnField
    )

    return piece
  }
}