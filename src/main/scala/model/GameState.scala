package model

import util.Observable

case class GameState(var playersList: List[Player], gameDice: Dice, gameBoard: GameBoard) extends Observable{
  def players: List[Player] = playersList

  private var currentPlayer: Player = players.head
  def dice: Dice = gameDice
  def board: GameBoard = gameBoard

  private var isRunning: Boolean = true

  def updateCurrentPlayer(player: Player): Unit = {
    currentPlayer = player
    notifyObservers(this)

  }

  def getCurrentPlayer(): Player = currentPlayer

  def updateRunningState(isRunning: Boolean): Unit = {
    this.isRunning = isRunning
    notifyObservers(this)
  }

  def getRunningState(): Boolean = isRunning

  def nextTurn(): Unit = {
    val currentIndex = players.indexOf(currentPlayer)
    val nextPlayer = players((currentIndex + 1) % players.size)

    updateCurrentPlayer(nextPlayer)
  }
}

