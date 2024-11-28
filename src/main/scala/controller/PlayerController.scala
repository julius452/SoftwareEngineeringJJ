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

    // Prototype pattern
    val initPlayer = Player(1, "")
    initPlayer.initializeHousesAndPieces()

    for (i <- 1 to playersCount) {
      println(consoleView.displayAskForPlayerName(i))
      val playerName = scala.io.StdIn.readLine()

      // work with clone
      val newPlayer= initPlayer.clone()
      newPlayer.setPlayerId(i)
      newPlayer.setPlayerName(playerName)

      players = players :+ newPlayer
    }

    return players
  }
}

