package controller

import model.Player
import view.ConsoleView

class PlayerController() {
  def initializePlayer(playerName: String, playerId: Int): Player = {
    val newPlayer = Player(playerId, playerName)
    newPlayer.initializeHousesAndPieces()

    newPlayer
  }

  def getStartingPlayer(playersMap: Map[Player, Int]): Player = {
    playersMap.maxBy(_._2)._1
  }
}

