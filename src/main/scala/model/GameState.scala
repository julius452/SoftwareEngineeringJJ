package model

case class GameState(
  players: List[Player],       // Liste aller Spieler
  var currentPlayer: Player,        // Der aktuell am Zug befindliche Spieler
  dice: Dice,                   // Der Würfel
  board: GameBoard,              // Das Spielfeld
  var isRunning: Boolean     // Gibt an, ob das Spiel noch läuft
)

