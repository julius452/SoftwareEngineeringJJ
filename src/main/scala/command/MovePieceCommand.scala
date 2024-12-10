package command

import controller.GameBoardController
import model.{GameState, Piece, Field}


class MovePieceCommand(gameBoardController: GameBoardController, gameState: GameState, piece: Piece, steps: Int) extends Command {
  private var previousField = Option(piece.getField())
  private var previousTraveledFields = piece.getTraveledFields()

  override def doStep(): Unit = {
    gameBoardController.movePiece(gameState, piece, steps)
  }

  override def undoStep(): Unit = {
    previousField.foreach(field => {
      piece.getField().setIsOccupied(false)
      field.setIsOccupied(true)
      piece.setField(field)
      piece.setTravelFields(previousTraveledFields)
    })
  }

  override def redoStep(): Unit = {
    doStep()
  }
}
