package controller

import model.{Field, GameState, Piece, Player}

class GameBoardController {
  def movePiece(gameState: GameState, piece: Piece, steps: Int): Unit = {
    if (piece.getIsOnField()) {
      val fields = gameState.board.getFields()

      val newTraveledFields = piece.getTraveledFields() + steps

      if (newTraveledFields > 39) {

        val restSteps = newTraveledFields - 39

        if (restSteps <= piece.player.getHouse().length) {
          piece.getField().setIsOccupied(false)

          val landingField = piece.player.getHouse()(restSteps-1)
          piece.setField(landingField)

          landingField.setPiece(Some(piece))
          landingField.setIsOccupied(true)

          piece.setIsInHome(true)
          piece.setIsOnField(false)
        }
      } else {
        val newIndex = (piece.getField().getPosition() + steps) % fields.length
        val landingField = fields(newIndex)

        if (landingField.getIsOccupied()) {
          val occupyingPiece = landingField.getPiece().get

          if (occupyingPiece.player != piece.player) {
            throwPlayerOut(occupyingPiece.player, piece, landingField, gameState)
          }
        }

        piece.getField().setIsOccupied(false)
        landingField.setIsOccupied(true)

        piece.setField(landingField)
        piece.setTravelFields(newTraveledFields)

        landingField.setPiece(Some(piece))
      }
    }
    else {
      val start = piece.player.getStartPosition()
      val fields = gameState.board.getFields()

      val startField = fields(start)

      piece.getField().setIsOccupied(false)

      piece.setField(startField)

      startField.setPiece(Some(piece))
      startField.setIsOccupied(true)

      piece.setIsOnField(true)
      piece.setTravelFields(0)
    }
  }

  def throwPlayerOut(throwingPlayer: Player, piece: Piece, landingField : Field, gameState: GameState): Unit = {
    val throwingPiece = landingField.getPiece()
    val returnToStartField = throwingPlayer.getStartHouse()(throwingPiece.get.id - 1)

    landingField.setIsOccupied(false)
    landingField.setPiece(None)

    returnToStartField.setIsOccupied(true)
    returnToStartField.setPiece(throwingPiece)

    throwingPiece.get.setField(returnToStartField)
    throwingPiece.get.setIsOnField(false)
    throwingPiece.get.setTravelFields(0)

    piece.getField().setIsOccupied(false)
    piece.getField().setPiece(None)

    piece.setField(landingField)

    val traveledFields = piece.getTraveledFields()

    piece.setTravelFields(traveledFields + gameState.dice.getLastRoll())
    landingField.setPiece(Some(piece))
    landingField.setIsOccupied(true)
  }
}
