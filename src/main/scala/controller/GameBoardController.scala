package controller

import model.{GameBoard, Piece, GameState}

class GameBoardController {
  def initializeGameBoard(): GameBoard = {
    val board = Array.fill(40)("00")

    // Startfelder
    for (i <- 0 to 3) {
      board(i * 10) = "ST"
      board(i * 10) = "ST"
      board(i * 10) = "ST"
      board(i * 10) = "ST"
    }
    return GameBoard(board)
  }

  def movePiece(gameState: GameState, piece: Piece, steps: Int): Unit = {
    if (piece.isOnField) {
      val fields = gameState.board.fields
      fields(piece.position) = "00"

      val newIndex = (piece.position + steps) % fields.length
      fields(newIndex) = s"${piece.player.id + piece.id}"

      piece.position = newIndex
    }
    if (!piece.isOnField) {
      val start = piece.player.startPosition
      val fields = gameState.board.fields
      fields(start) = s"${piece.player.id + piece.id}"

      piece.isOnField = true
      piece.position = start
    }
  }
}
