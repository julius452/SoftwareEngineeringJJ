package controller

import model.{Field, GameState, Piece, Player}
import strategy.{MoveStrategy, NormalMoveStrategy, CollisionMoveStrategy}

class RuleController() {
  private val gameBoardController = new GameBoardController()


  def executeMove(piece: Piece, gameState: GameState): Unit = {
    val steps = gameState.dice.getLastRoll()
    val landingField = gameState.board.getFields()((piece.getField().getPosition() + steps) % gameState.board.getFields().length)

    val strategy: MoveStrategy = if (landingField.getIsOccupied()) {
      new CollisionMoveStrategy()
    } else {
      new NormalMoveStrategy()
    }

    strategy.movePiece(gameBoardController, gameState, piece, steps)
  }

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
    val startField = gameState.board.getFields()(player.getStartPosition())
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
      if(gameState.dice.getLastRoll() == 6 && isStartFieldFree(piece.player, gameState)) {
        return false
      }
      val landingIndex = (piece.getField().getPosition() + gameState.dice.getLastRoll()) % gameState.board.getFields().length
      val landingField = gameState.board.getFields()(landingIndex)
      if (landingField.getIsOccupied()) {
        val standingPlayer = landingField.getPiece().get.player
        if (standingPlayer.getPlayerId().equals(piece.player.getPlayerId())) {
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
