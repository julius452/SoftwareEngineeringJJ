package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers {

  private val dice = Dice()

  "A Dice" should {
    "roll Dice" in {
      dice.rollDice()

      dice.getLastRoll() should (be >= 1 and be <= 6)
    }

    "get last roll" in {
      dice.setLastRoll(6)

      dice.getLastRoll() shouldBe 6
    }

    "set last roll" in {
      dice.setLastRoll(5)

      dice.getLastRoll() shouldBe 5
    }
  }
}