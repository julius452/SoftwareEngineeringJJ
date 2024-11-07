package controller

import model.{Field, Piece, Player}
import controller.PieceController

class PlayerController() {
  private val pieceController = new PieceController()
  private val fieldController = new FieldController()
  private val playerId = Array("A", "B", "C", "D")

  def initializePlayer(id: Int, name: String): Player = {
    val player = Player(
      id = playerId(id - 1),
      name = name,
      pieces = new Array[Piece](4),
      house = new Array[Field](4),
      startHouse = new Array[Field](4),
      startPosition = (id-1) * 10
    )

    for (i <- 0 to 3) {
      player.startHouse(i) = fieldController.initializeStartHouseField()

      player.pieces(i) = pieceController.initializePiece(player, i + 1)
    }

    for (i <- 0 to 3) {
      player.house(i) = fieldController.initializeHomeField(i)
    }

    return player
  }

  def checkIfAllPiecesOffField(player: Player): Boolean = {
    var allPiecesOffField = true

    for (piece <- player.pieces) {
      if (piece.isOnField) {
        return false
      }
    }

    return allPiecesOffField
  }

  /*def enterPiece(player: Player): Unit = {
    if (canEnterPiece(player)) {
      val piece = player.pieces.find(_.position.isEmpty)
      piece.foreach { p =>
        val startPos = player.startPosition
        updatePiecePosition(p, startPos)
      }
    }
  }

  def canEnterPiece(player: Player): Boolean = {
    // gameState.get.board.positions(player.startPosition).isEmpty
    false
  }

  def choosePieceToMove(player: Player): Option[Piece] = {
    player.pieces.find(p => p.position.isDefined && !p.isAtHome)
  }

  private def updatePiecePosition(piece: Piece, position: Int): Unit = {
    // val updatedBoard = gameState.board.positions.updated(position, Some(piece))
    // gameState.board.copy(positions = updatedBoard)
  }*/
}

