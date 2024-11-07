package controller

import model.{GameBoard, Piece, GameState, Field}

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
          var landingField = piece.player.house(restSteps-1)
          piece.field = landingField

          piece.isInHome = true
          piece.isOnField = false
          println(s"Piece ${piece.id} für Spieler ${piece.player.id} ist im Haus angekommen an Stelle: $restSteps.")
        }

      } else {
        val newIndex = (piece.field.position + steps) % fields.length
        val landingField = fields(newIndex)
        piece.field = landingField

        piece.traveledFields = newTraveledFields
      }
    }
    if (!piece.isOnField) {
      val start = piece.player.startPosition
      val fields = gameState.board.fields

      val landingField = fields(start)
      piece.field = landingField

      piece.traveledFields = 0
      piece.isOnField = true
    }
  }
}
