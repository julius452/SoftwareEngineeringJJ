package model

trait ModelInterface {
  def checkNumberOfPlayers(number: Int): Boolean
  def setPlayersCount(numberOfPlayer: Int): Unit
  def addPlayer(playerName: String): Unit
}
