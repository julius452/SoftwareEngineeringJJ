package controller

import model.PlayboardModel
import view.PlayboardView

class PlayboardController (model: PlayboardModel, view: PlayboardView.type) {
  def printBoard(): String = {
    // Model initialisieren
    model.initializePlayboard()

    // Spielfeld anzeigen
    view.printBoard(model.getPlayboard, model.getHouse1, model.getHouse2, model.getHouse3, model.getHouse4)
  }
}
