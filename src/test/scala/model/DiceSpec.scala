package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers {

  "A Dice" should {
    "store the last roll" in {
      val dice = Dice(5)
      dice.lastRoll shouldBe 5

      // Update last roll
      dice.lastRoll = 3
      dice.lastRoll shouldBe 3
    }
  }
}