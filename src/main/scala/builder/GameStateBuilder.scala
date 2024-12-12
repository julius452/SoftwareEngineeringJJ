package builder

import model.{Dice, GameBoard, GameState, Player}

import scala.compiletime.uninitialized

class GameStateBuilder extends BobTheBuilder {
  private var gameDice: Dice = null
  private var gameBoard: GameBoard = null

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
    GameState(gameDice, gameBoard)
  }
}
