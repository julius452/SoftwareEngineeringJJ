package controller

import model.{Piece, Player}

class PieceController {
  private val fieldController = new FieldController()

  def initializePiece(player: Player, id: Int): Piece = {
    val position = -1
    val traveledFiels = 0
    val isInHome, isOnField = false
    val field = player.startHouse(id - 1)

    val piece = Piece(
      player,
      id,
      traveledFiels,
      isInHome,
      isOnField,
      field
    )

    return piece
  }
}
