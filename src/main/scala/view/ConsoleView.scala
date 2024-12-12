package view

import model.{GameBoard, GameState, Piece, Player}
import util.Observer

object ConsoleView{
  def displayStartPhase(): String = {
    var sb = new StringBuilder()
    sb.append("Willkommen bei Mensch Ärgere Dich Nicht!\n")
    sb.append("Bitte geben Sie die Anzahl der Spieler ein (2-4):")

    sb.toString()
  }

  def displayPlayerSetupPhase(index: Int): String = {
    val sb = new StringBuilder()
    sb.append(s"Name des Spielers $index:")

    sb.toString()
  }

  def displayDetermineStartPlayerPhase(gameState: GameState): String = {
    val sb = new StringBuilder()
    if (gameState.gameDice.getLastRoll() != 0) {
      sb.append(displayDiceRoll(gameState.gameDice.getLastRoll(), gameState.getCurrentPlayer().getPlayerName()))
    }

    sb.append(displayDivider())
    sb.append("Der Startspieler wird ermittelt.\n")
    sb.append(displayPleaseRoll(gameState.getCurrentPlayer()))
    sb.toString()
  }

  def displayPleaseRoll(currentPlayer: Player): String = {
    s"${currentPlayer.getPlayerName()}, bitte würfeln (w):"
  }

  def displayDiceRoll(roll: Int, name: String): String = {
    if (roll == 6) {
      return s"Yeah!, $name hat eine 6 gewürfelt\n"
    }

    s"$name hat eine $roll gewürfelt.\n"
  }

  def displayInGamePhaseString(gameState: GameState): String = {
    val sb = new StringBuilder()

    if (gameState.gameDice.getLastRoll() != 0) {
      sb.append(displayDiceRoll(gameState.gameDice.getLastRoll(), gameState.getCurrentPlayer().getPlayerName()))
    }

    if (gameState.getTriesToGetOutOfStartHouse == 0) {
      sb.append(displayDivider())

      sb.append(s"${gameState.getCurrentPlayer().getPlayerName()} ist am Zug.\n")

      sb.append(getGameBoardAsString(gameState))

      sb.append(getPlayerHouseAsString(gameState))

      sb.append(getPlayerStartHouseAsString(gameState))

      sb.append('\n')
    } else {
      sb.append("\nDu kannst noch nicht aus dem Haus ziehen!\n")
      sb.append("Du hast noch " + (3 - gameState.getTriesToGetOutOfStartHouse) + " Versuch(e)\n")
      sb.append("-------------------------")
    }

    sb.append('\n')

    sb.append(displayPleaseRoll(gameState.getCurrentPlayer()))

    sb.toString()
  }

  def displayHappyWithPlayers(): String = {
    return "Sind Sie mit den Spielernamen zufrieden? (j/n):"
  }

  def displayPlayers(players: List[Player]): String = {
    val sb = new StringBuilder()
    sb.append("\nSpieler:")
    sb.append("\n")
    for (player <- players) {
      sb.append(s"\tSpieler ${player.getPlayerNumber()}: ${player.getPlayerName()}\n")
    }

    return sb.toString()
  }

  def displayChangePlayer(): String = {
    return "Welchen Spieler möchten Sie ändern?:"
  }

  def displayAskForNewPlayerName(playerNumber: Int): String = {
    return s"Neuer Name für Spieler $playerNumber:"
  }

  def displayPlayerWon(player: Player): String = {
    return s"\nGlückwunsch ${player.getPlayerName()} Du hat gewonnen!"
  }

  def displayPlayerCanEnterPiece(): String = {
    return s"\nDeine Möglichen Züge:\n"
  }

  def displayWhichPieceToMove(): String = {
    return "Welche Figur soll ziehen?:"
  }

  def displayValideMove(piece: Piece): String = {
    val sb = new StringBuilder()

    if (piece.getIsOnField()) {
      return s"\tFigur ${piece.player.getPlayerId() + piece.id} (${piece.id}) auf Feld ${piece.getField().getPosition()} kann ziehen.\n"
    }

    if (piece.getIsInHome()) {
      return s"\tFigur ${piece.player.getPlayerId() + piece.id} (${piece.id}) kann im Haus ziehen.\n"
    }

    s"\tFigur ${piece.player.getPlayerId() + piece.id} (${piece.id}) kann auf das Spielfeld gesetzt werden.\n"
  }

  def displayGameBoard(gameState: GameState): String = {
    val sb = new StringBuilder()
    sb.append(getGameBoardAsString(gameState))

    sb.append(getPlayerHouseAsString(gameState))

    sb.append(getPlayerStartHouseAsString(gameState))

    sb.append('\n')
    return sb.toString()
  }

  def getGameBoardAsString(gameState: GameState): String = {
    val sb = new StringBuilder()
    sb.append("Spielfeld:")
    sb.append("\n")
    sb.append("\t")

    val boardFields = new Array[String](40)

    for (i <- 0 until 40) {
      val field = gameState.board.getFields()(i)

      if (field.getIsOccupied()) {
        boardFields(i) = s"${field.getPiece().get.player.getPlayerId() + field.getPiece().get.id}"
      } else {
        boardFields(i) = s"${field.getValue()}"
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
      val field = gameState.getCurrentPlayer().getHouse()(i)

      if (field.getIsOccupied()) {
        houseFields(i) = s"${field.getPiece().get.player.getPlayerId() + field.getPiece().get.id}"
      } else {
        houseFields(i) = s"${field.getValue()}"
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
      val field = gameState.getCurrentPlayer().getStartHouse()(i)

      if (field.getIsOccupied()) {
        startHouseFields(i) = s"${field.getPiece().get.player.getPlayerId() + field.getPiece().get.id}"
      } else {
        startHouseFields(i) = s"${field.getValue()}"
      }
    }

    val startHouseString = startHouseFields.mkString(" | ")
    sb.append(startHouseString)

    return sb.toString()
  }

  def displayDivider(): String = {
    val sb = new StringBuilder()
    sb.append("-"*70)
    sb.append("\n")
    return sb.toString()
  }

  def displaNoValidMoves(): String = {
    "Keine gültigen Züge für den Spieler."
  }

  def displayNextMove(): String = {
    "Der nächste Zug wird ausgeführt."
  }

  def displayUndoOption(): String = {
    "Möchten Sie den Zug rückgängig machen? Drücken Sie (u) für Ja, oder eine andere Taste für Nein."
  }

  def displayRedoOption(): String = {
    "Möchten Sie den letzten Zug wiederherstellen? Drücken Sie (r) für Ja, oder eine andere Taste für Nein."
  }

  def displayErrorUndo(): String = {
    "Kein Zug zum Rückgängig machen."
  }

  def displayErrorRedo(): String = {
    "Kein Zug zum Wiederholen."
  }

  def displayError(): String = {
    "Fehler beim Wiederholen des Befehls: ${exception.getMessage}"
  }
}
