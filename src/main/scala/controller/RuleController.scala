package controller

import model.{GameState, Piece, Player, Field}

class RuleController() {
  private val gameBoardController = new GameBoardController()

  def checkCollision(piece: Piece, landingField: Field, gameState: GameState): Boolean = {
    if (landingField.isOccupied) {
      val standingPlayer = landingField.piece.get.player
      gameBoardController.throwPlayerOut(standingPlayer, piece, landingField, gameState)
      return true
    } else {
      return false
    }
  }


  def isStartFieldFree(player: Player, gameState: GameState): Boolean = {
    val startField = gameState.board.fields(player.startPosition)
    if (startField.isOccupied) { //startField.piece.get.player.id.equals(player.id)
      return false
    } else {
      return true
    }
  }

  def validateMove(piece: Piece, gameState: GameState): Boolean = {
    if (!piece.isOnField && gameState.dice.lastRoll == 6) {
      if(isStartFieldFree(piece.player, gameState)){
        return true
      } else {
        return false
      }
    }

    // checken ob man mit der gewürfelten Zahl ins Haus kommt
    if (piece.isOnField) {
      val landingIndex = (piece.field.position + gameState.dice.lastRoll) % gameState.board.fields.length
      val landingField = gameState.board.fields(landingIndex)
      if (landingField.isOccupied) {
        val standingPlayer = landingField.piece.get.player
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

