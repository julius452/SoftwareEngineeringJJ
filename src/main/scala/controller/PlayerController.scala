package controller

import memento.Caretaker
import model.Player
import view.{ConsoleView, InputHandler}

class PlayerController() {
  private val consoleView = new ConsoleView()
  private val inputHandler = new InputHandler()
  def displayInitializePlayers(): List[Player] = {
    println(consoleView.displayAskForPlayersCount())

    var inputValid = false
    var playersCount = 0
    while (!inputValid) {
      playersCount = inputHandler.readInt()

      if (playersCount >= 2 && playersCount <= 4) {
        inputValid = true
      } else {
        println(consoleView.displayWrongInput())
        println(consoleView.displayAskForPlayersCount())
      }
    }

    ininitializerPlayers(playersCount)
  }

  def ininitializerPlayers(playerCount: Int): List[Player] = {
    var players = List[Player]()
    val caretaker = new Caretaker()

    for (i <- 1 to playerCount) {
      println(consoleView.displayAskForPlayerName(i))
      val playerName = inputHandler.readLine()

      val newPlayer = Player(i, playerName)
      newPlayer.initializeHousesAndPieces()

      // Memento pattern
      caretaker.save(newPlayer)

      players = players :+ newPlayer
    }

    happyWithPlayers(players, caretaker)

    players
  }

  def happyWithPlayers(players: List[Player], caretaker: Caretaker): Unit = {
    var isHappy = false

    while (!isHappy) {
      println(consoleView.displayPlayers(players))
      println(consoleView.displayHappyWithPlayers())

      val happy = inputHandler.readLine()

      if (happy == "j") {
        isHappy = true
        return
      }

      println(consoleView.displayChangePlayer())
      val playerNumberToChange = scala.io.StdIn.readInt()

      val player = players(playerNumberToChange - 1)

      //undo memento
      caretaker.undo(player)

      println(consoleView.displayAskForNewPlayerName(playerNumberToChange))

      val newPlayerName = inputHandler.readLine()

      caretaker.save(player)
      player.setPlayerName(newPlayerName)
    }
  }
}

