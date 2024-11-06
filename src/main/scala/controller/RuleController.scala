package controller

import model.{GameState, Piece, Player}

class RuleController(gameState: GameState) {
  def checkCollision(piece: Piece, newPosition: Int): Boolean = {
    //gameState.board.positions(newPosition).exists(_.player != piece.player)
    false
  }

  def isStartFieldFree(player: Player): Boolean = {
    //gameState.board.positions(player.startPosition).isEmpty
    false
  }

  def validateMove(piece: Piece, steps: Int): Boolean = {
    //val newPosition = (piece.position.getOrElse(0) + steps) % gameState.board.positions.size
    //!checkCollision(piece, newPosition)
    false
  }

  def canEnterGoal(piece: Piece, steps: Int): Boolean = {
    /*val goalPos = piece.player.goalPositions
    val newPosition = piece.position.getOrElse(0) + steps
    goalPos.contains(newPosition) && goalPos.indexOf(newPosition) == piece.player.pieces.indexOf(piece) */
    false
  }
}

