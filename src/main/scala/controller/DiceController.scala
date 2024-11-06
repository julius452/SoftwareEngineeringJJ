package controller

import model.Dice

class DiceController {

  def inInitializeDice(): Dice = {
    return Dice(lastRoll = 0)
  }

  def rollDice(dice: Dice): Unit = {
    val roll = scala.util.Random.nextInt(6) + 1
    dice.lastRoll = roll
  }

}
