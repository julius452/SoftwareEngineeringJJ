package controller

import model.{GameState, Player}

class GameStateController {
  def initializeGameState(players: List[Player]): GameState = {
    val startingPlayer = players.head
    val dice = new DiceController().inInitializeDice()
    val board = new GameBoardController().initializeGameBoard()
    val isRunning = true

    return GameState(players, startingPlayer, dice, board, isRunning)
  }

  def updateCurrentPlayer(gameState: GameState, player: Player): Unit = {
    gameState.currentPlayer = player
  }

  def updateRunningState(gameState: GameState, isRunning: Boolean): Unit = {
    gameState.isRunning = isRunning
  }

  def nextTurn(gameState: GameState): Unit = {
    val currentIndex = gameState.players.indexOf(gameState.currentPlayer)
    val nextPlayer = gameState.players((currentIndex + 1) % gameState.players.size)

    updateCurrentPlayer(gameState, nextPlayer)
  }
}
