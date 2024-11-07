package controller

import model.{Piece, Player}

class PieceController {
  private val fieldController = new FieldController()

  def initializePiece(player: Player, id: Int): Piece = {
    val traveledFields = 0
    val isInHome, isOnField = false
    val field = player.startHouse(id - 1)

    val piece = Piece(
      player,
      id,
      traveledFields,
      isInHome,
      isOnField,
      field
    )

    return piece
  }
}
