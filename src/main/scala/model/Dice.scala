package model

case class Dice() {
  private var lastRoll: Int = 0

  def rollDice(): Unit = {
    this.lastRoll = scala.util.Random.nextInt(6) + 1
  }

  def getLastRoll(): Int = {
    lastRoll
  }

  def setLastRoll(roll: Int): Unit = {
    lastRoll = roll
  }
}

