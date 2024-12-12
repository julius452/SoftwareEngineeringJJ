package controller

import memento.Caretaker
import model.Player
import view.ConsoleView

import scala.util.Try

class PlayerController() {
  /* def happyWithPlayers(players: List[Player], caretaker: Caretaker): Unit = {
    var isHappy = false

    while (!isHappy) {
      //println(consoleView.displayPlayers(players))
      //println(consoleView.displayHappyWithPlayers())

      val happy = scala.io.StdIn.readLine()

      if (happy == "j") {
        isHappy = true
        return
      }

      //println(consoleView.displayChangePlayer())
      val playerNumberToChange = scala.io.StdIn.readInt()

      val player = players(playerNumberToChange - 1)

      //undo memento
      caretaker.undo(player)

      //println(consoleView.displayAskForNewPlayerName(playerNumberToChange))

      val newPlayerName = scala.io.StdIn.readLine()

      caretaker.save(player)
      player.setPlayerName(newPlayerName)
    }
  } */
}

