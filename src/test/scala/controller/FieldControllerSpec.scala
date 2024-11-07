package controller

import model.{Field, GameBoard, GameState, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach

class FieldControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  "A FieldController" should {
    val fieldController = new FieldController()

    "initialize a Start Field" in {
      val startField = fieldController.initializeStartField(0)
      startField.value shouldBe "ST"
      startField.isStartField shouldBe true
      startField.isHouseField shouldBe false
    }

    "initialize a Game Field" in {
      val gameField = fieldController.initializeGameField(1)
      gameField.value shouldBe "00"
      gameField.isStartField shouldBe false
      gameField.isHouseField shouldBe false
    }

    "initialize a Home Field" in {
      val homeField = fieldController.initializeHomeField(2)
      homeField.value shouldBe "00"
      homeField.isStartField shouldBe false
      homeField.isHouseField shouldBe true
    }
  }
}
