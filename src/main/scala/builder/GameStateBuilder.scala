package builder

import model.{Dice, GameBoard, GameState, Player}

class GameStateBuilder {
  private var playersList: List[Player] = List.empty
  private var gameDice: Dice = _
  private var gameBoard: GameBoard = _

  def buildPlayers(players: List[Player]): GameStateBuilder = {
    this.playersList = players
    this
  }

  def buildDice(): GameStateBuilder = {
    this.gameDice = Dice()
    this
  }

  def buildGameBoard(): GameStateBuilder = {
    this.gameBoard = GameBoard()
    this.gameBoard.initializeGameBoard()
    this
  }

  // Baut das GameState-Objekt
  def build(): GameState = {
    GameState(playersList, gameDice, gameBoard)
  }
}
