package view

import model.{GameBoard, GameState, Player}

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
    return s"${player.name} kann eine Figur auf das Spielfeld setzen."
  }

  def displayPlayerPossibleMoves(player: Player): String = {
    var sb = new StringBuilder()
    sb.append(s"\nFolgende Figuren können bewegt werden:")
    sb.append("\n")
    for (piece <- player.pieces) {
      if (piece.isOnField) {
        sb.append("\t")
        sb.append(s"Figur ${piece.player.id + piece.id} (${piece.id}) auf Feld ${piece.position} kann ziehen")
        sb.append("\n")
      } else {
        sb.append("\t")
        sb.append(s"Figur ${piece.player.id + piece.id} (${piece.id}) kann auf das Spielfeld gesetzt werden")
        sb.append("\n")
      }
    }
    sb.append("Welche Figur soll ziehen?:")

    return sb.toString()
  }

  def displayGameBoard(gameState: GameState): String = {
    val sb = new StringBuilder()
    sb.append("Spielfeld:")
    sb.append("\n")
    sb.append("\t")
    val boardString = gameState.board.fields.mkString(" | ")
    sb.append(boardString)
    sb.append("\n")

    sb.append("Haus:")
    sb.append("\n")
    sb.append("\t")
    val houseString = gameState.currentPlayer.house.mkString(" | ")
    sb.append(houseString)
    sb.append("\n")

    sb.append("Starthäuschen:")
    sb.append("\n")
    sb.append("\t")
    for (piece <- gameState.currentPlayer.pieces) {
      if (!piece.isOnField) {
        sb.append(s"${piece.player.id + piece.id}")
        sb.append(" | ")
      } else {
        sb.append("00 | ")
      }
    }

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
