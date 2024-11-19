package view

import controller.GameController

class GameInputHandler(controller: GameController) {
  def checkDiceRollInput(input: String): Boolean = {
    if (input == "w") {
      true
    } else {
      false
    }
  }
}
