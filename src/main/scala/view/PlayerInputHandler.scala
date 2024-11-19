package view

import controller.PlayerController
import model.Player

class PlayerInputHandler(controller: PlayerController) {
  def checkPlayerCountInput(input: Int): Boolean = {
    if (input >= 2 && input <= 4) {
      true
    } else {
      false
    }
  }

  def handlePlayerNameInput(name: String, id: Int): Player = {
    controller.initializePlayer(name, id)
  }
}
