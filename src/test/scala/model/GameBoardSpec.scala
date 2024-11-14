import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.{GameBoard, FieldType, Field}

class GameBoardSpec extends AnyWordSpec with Matchers {

  "A GameBoard" should {

    "have an array of 40 uninitialized fields by default" in {
      val gameBoard = GameBoard()
      gameBoard.getFields().length shouldBe 40
      all(gameBoard.getFields()) shouldBe null
    }

    "initialize the game board with fields set correctly" in {
      val gameBoard = GameBoard()
      gameBoard.initializeGameBoard()
      val fields = gameBoard.getFields()

      fields.length shouldBe 40

      for (i <- 0 until 40) {
        fields(i) should not be null
        fields(i).getPosition() shouldBe i

        if (i % 10 == 0) {
          fields(i).getValue() shouldBe "ST"
          fields(i).getIsStartField() shouldBe true
        } else {
          fields(i).getValue() shouldBe "00"
          fields(i).getIsStartField() shouldBe false
        }

        fields(i).getIsOccupied() shouldBe false
        fields(i).getPiece() shouldBe None
        fields(i).getIsHouseField() shouldBe false
      }
    }

    "set every 10th field as a start field and others as game fields" in {
      val gameBoard = GameBoard()
      gameBoard.initializeGameBoard()
      val fields = gameBoard.getFields()

      for (i <- 0 until 40) {
        if (i % 10 == 0) {
          fields(i).getIsStartField() shouldBe true
          fields(i).getValue() shouldBe "ST"
        } else {
          fields(i).getIsStartField() shouldBe false
          fields(i).getValue() shouldBe "00"
        }
      }
    }
  }
}
