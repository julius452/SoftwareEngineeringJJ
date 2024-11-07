package view

import model.{GameBoard, GameState, Piece, Player}

class ConsoleView {
  def displayAskForPlayersCount(): String = {
    return "Wie viele spielen mit? (2-4):"
  }

  def displayAskForPlayerName(i: Int): String = {
    return s"Name des Spielers $i:"
  }

  def displayDetermineStartingPlayer(): String = {
    return "Um den Startspieler zu bestimmen, müssen alle Spieler einmal würfeln:"
  }

  def displayAskPlayerToRoll(player: Player): String = {
    return s"${player.name}, bitte würfeln (w):"
  }

  def displayDiceRollResult(player: Player, roll: Int): String = {
    return s"${player.name} hat eine $roll gewürfelt.\n"
  }

  def displayStartPlayer(player: Player): String = {
    return s"${player.name} beginnt!"
  }

  def displayTurnInfo(player: Player): String = {
    return s"${player.name} ist am Zug."
  }

  def displayWrongInput(): String = {
    return "\nFalsche Eingabe!! Nochmal versuchen:\n"
  }

  def displayPlayerWon(player: Player): String = {
    return s"\nGlückwunsch ${player.name} Du hat gewonnen!"
  }

  def displayPlayerCanEnterPiece(player: Player): String = {
    return s"\nMögliche Züge:."
  }

  def displayWhichPieceToMove(): String = {
    return "Welche Figur soll ziehen?:"
  }

  def displayValideMove(piece: Piece): String = {
    val sb = new StringBuilder()

    if (piece.isOnField) {
      return s"\tFigur ${piece.player.id + piece.id} (${piece.id}) auf Feld ${piece.field.position} kann ziehen."
    }

    if (piece.isInHome) {
      return s"\tFigur ${piece.player.id + piece.id} (${piece.id}) kann im Haus ziehen."
    }

    return s"\tFigur ${piece.player.id + piece.id} (${piece.id}) kann auf das Spielfeld gesetzt werden."
  }

  def displayGameBoard(gameState: GameState): String = {
    val sb = new StringBuilder()
    sb.append(getGameBoardAsString(gameState))

    sb.append(getPlayerHouseAsString(gameState))

    sb.append(getPlayerStartHouseAsString(gameState))

    return sb.toString()
  }

  def getGameBoardAsString(gameState: GameState): String = {
    val sb = new StringBuilder()
    sb.append("Spielfeld:")
    sb.append("\n")
    sb.append("\t")

    val boardFields = new Array[String](40)

    for (i <- 0 until 40) {
      val field = gameState.board.fields(i)

      if (field.isOccupied) {
        boardFields(i) = s"${field.piece.get.player.id + field.piece.get.id}"
      } else {
        boardFields(i) = s"${field.value}"
      }
    }

    val boardString = boardFields.mkString(" | ")
    sb.append(boardString)
    sb.append("\n")

    return sb.toString()
  }

  def getPlayerHouseAsString(gameState: GameState): String = {
    val sb = new StringBuilder()

    sb.append("Haus:")
    sb.append("\n")
    sb.append("\t")

    val houseFields = new Array[String](4)
    for (i <- 0 until 4) {
      val field = gameState.currentPlayer.house(i)

      if (field.isOccupied) {
        houseFields(i) = s"${field.piece.get.player.id + field.piece.get.id}"
      } else {
        houseFields(i) = s"${field.value}"
      }
    }

    val houseString = houseFields.mkString(" | ")
    sb.append(houseString)
    sb.append("\n")

    return sb.toString()
  }

  def getPlayerStartHouseAsString(gameState: GameState): String = {
    val sb = new StringBuilder()

    sb.append("Starthäuschen:")
    sb.append("\n")
    sb.append("\t")

    val startHouseFields = new Array[String](4)
    for (i <- 0 until 4) {
      val field = gameState.currentPlayer.startHouse(i)

      if (field.isOccupied) {
        startHouseFields(i) = s"${field.piece.get.player.id + field.piece.get.id}"
      } else {
        startHouseFields(i) = s"${field.value}"
      }
    }

    val startHouseString = startHouseFields.mkString(" | ")
    sb.append(startHouseString)

    return sb.toString()
  }

  def displayPlayerCanRollAgain(player: Player): String = {
    return s"\n${player.name} hat eine 6 gewürfelt und darf nochmal würfeln."
  }

  def displayDivider(): String = {
    val sb = new StringBuilder()
    sb.append("\n")
    sb.append("-"*70)
    sb.append("\n")
    return sb.toString()
  }
}
