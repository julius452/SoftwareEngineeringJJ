package controller

import model.{Field, GameBoard, GameState, Piece, Player}

class GameBoardController {
  val fieldController = new FieldController()

  def initializeGameBoard(): GameBoard = {
    val board = new Array[Field](40)

    for (i <- 0 until 40) {
      if (i % 10 == 0) {
        board(i) = fieldController.initializeStartField(i)
      } else {
        board(i) = fieldController.initializeGameField(i)
      }
    }

    return GameBoard(board)
  }

  def movePiece(gameState: GameState, piece: Piece, steps: Int): Unit = {
    if (piece.isOnField) {
      val fields = gameState.board.fields

      val newTraveledFields = piece.traveledFields + steps

      if (newTraveledFields > 39) {

        val restSteps = newTraveledFields - 39

        if (restSteps <= piece.player.house.length) {
          piece.field.isOccupied = false

          val landingField = piece.player.house(restSteps-1)
          piece.field = landingField

          landingField.piece = Some(piece)
          landingField.isOccupied = true

          piece.isInHome = true
          piece.isOnField = false
        }

      } else {
        val newIndex = (piece.field.position + steps) % fields.length
        val landingField = fields(newIndex)

        piece.field.isOccupied = false
        landingField.isOccupied = true

        piece.field = landingField
        piece.traveledFields = newTraveledFields

        landingField.piece = Some(piece)
      }
    }
    else {
      val start = piece.player.startPosition
      val fields = gameState.board.fields

      val landingField = fields(start)

      piece.field.isOccupied = false

      piece.field = landingField

      landingField.piece = Some(piece)
      landingField.isOccupied = true

      piece.traveledFields = 0
      piece.isOnField = true
    }
  }


  def throwPlayerOut(throwingPlayer: Player, piece: Piece, landingField : Field, gameState: GameState): Unit = {
    val throwingPiece = landingField.piece
    val returnToStartField = throwingPlayer.startHouse(throwingPiece.get.id - 1)
    returnToStartField.isOccupied = true
    returnToStartField.piece = throwingPiece
    throwingPiece.get.field = returnToStartField
    throwingPiece.get.isOnField = false
    throwingPiece.get.traveledFields = 0
    movePiece(gameState, piece, gameState.dice.lastRoll)
  }
}
