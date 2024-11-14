package controller

import model.{GameState, Piece, Player, Field}

class RuleController() {
  private val gameBoardController = new GameBoardController()

  def checkCollision(piece: Piece, landingField: Field, gameState: GameState): Boolean = {
    if (landingField.getIsOccupied()) {
      val standingPlayer = landingField.getPiece().get.player
      gameBoardController.throwPlayerOut(standingPlayer, piece, landingField, gameState)
      return true
    } else {
      return false
    }
  }


  def isStartFieldFree(player: Player, gameState: GameState): Boolean = {
    val startField = gameState.board.getFields()(player.startPosition)
    if (startField.getIsOccupied()) { //startField.piece.get.player.id.equals(player.id)
      return false
    } else {
      return true
    }
  }

  def validateMove(piece: Piece, gameState: GameState): Boolean = {
    if (!piece.getIsOnField() && gameState.dice.getLastRoll() == 6) {
      if(isStartFieldFree(piece.player, gameState)){
        return true
      } else {
        return false
      }
    }

    // checken ob man mit der gewürfelten Zahl ins Haus kommt
    if (piece.getIsOnField()) {
      val landingIndex = (piece.getField().getPosition() + gameState.dice.getLastRoll()) % gameState.board.getFields().length
      val landingField = gameState.board.getFields()(landingIndex)
      if (landingField.getIsOccupied()) {
        val standingPlayer = landingField.getPiece().get.player
        if (standingPlayer.id.equals(piece.player.id)) {
          return false
        } else {
          return true
        }
      } else {
        return true
      }
    }

    return false
  }

}

