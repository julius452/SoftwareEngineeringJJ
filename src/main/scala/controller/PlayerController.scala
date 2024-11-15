package controller

import model.Player
import view.ConsoleView

class PlayerController() {
  private val consoleView = new ConsoleView()
  def initializePlayers(): List[Player] = {
    println(consoleView.displayAskForPlayersCount())

    var inputValid = false
    var playersCount = 0
    while (!inputValid) {
      playersCount = scala.io.StdIn.readInt()

      if (playersCount >= 2 && playersCount <= 4) {
        inputValid = true
      } else {
        println(consoleView.displayWrongInput())
        println(consoleView.displayAskForPlayersCount())
      }
    }

    var players = List[Player]()
    for (i <- 1 to playersCount) {
      println(consoleView.displayAskForPlayerName(i))
      val playerName = scala.io.StdIn.readLine()
      val newPlayer = Player(i, playerName)
      newPlayer.initializeHousesAndPieces()
      players = players :+ newPlayer
    }

    return players
  }
}

