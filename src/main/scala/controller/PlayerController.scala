package controller

import model.{Piece, Player}
import controller.PieceController

class PlayerController() {
  private val pieceController = new PieceController()
  private val playerId = Array("A", "B", "C", "D")

  def initializePlayer(id: Int, name: String): Player = {
    val player = Player(
      id = playerId(id - 1),
      name = name,
      pieces = new Array[Piece](4),
      house = Array.fill(4)("00"),
      startPosition = (id-1) * 10
    )

    for (i <- 0 to 3) {
      player.pieces(i) = pieceController.initializePiece(player, i + 1)
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

