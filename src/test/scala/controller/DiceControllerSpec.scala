package controller

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class DiceControllerSpec extends AnyWordSpec {

  "DiceController" should {

    "initialize dice correctly" in {
      val controller = new DiceController
      val dice = controller.inInitializeDice()
      dice.lastRoll shouldEqual 0
    }

    "roll dice and update lastRoll to a value between 1 and 6" in {
      val controller = new DiceController
      val dice = controller.inInitializeDice()

      controller.rollDice(dice)

      dice.lastRoll should (be >=1 and be <=6)
    }
  }

}