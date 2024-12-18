package controller

import memento.Caretaker
import model.Player
import view.ConsoleView

import scala.util.Try

class PlayerController() {
  private val consoleView = new ConsoleView()
  def initializePlayers(): List[Player] = {
    val caretaker = new Caretaker()

    println(consoleView.displayAskForPlayersCount())

    var inputValid = false
    var playersCount = 0
    while (!inputValid) {
      Try {
        playersCount = scala.io.StdIn.readInt()
      }.recover {
        case e: NumberFormatException => println(consoleView.displayWrongInput() + consoleView.displayAskForPlayersCount())
      }

      if (playersCount >= 2 && playersCount <= 4) {
        inputValid = true
      }
    }

    var players = List[Player]()

    for (i <- 1 to playersCount) {
      println(consoleView.displayAskForPlayerName(i))
      val playerName = scala.io.StdIn.readLine()

      //val newPlayer = Player(i, playerName)
      val newPlayer = Player(i, playerName)
      newPlayer.initializeHousesAndPieces()

      // Memento pattern
      caretaker.save(newPlayer)

      players = players :+ newPlayer
    }

    happyWithPlayers(players, caretaker)

    return players
  }

  def happyWithPlayers(players: List[Player], caretaker: Caretaker): Unit = {
    var isHappy = false

    while (!isHappy) {
      println(consoleView.displayPlayers(players))
      println(consoleView.displayHappyWithPlayers())

      val happy = scala.io.StdIn.readLine()

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

      val newPlayerName = scala.io.StdIn.readLine()

      caretaker.save(player)
      player.setPlayerName(newPlayerName)
    }
  }
}

