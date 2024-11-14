import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.{Field, FieldType, Piece, Player}

class FieldSpec extends AnyWordSpec with Matchers {

  "A Field" should {

    "have a default state with uninitialized values" in {
      val field = Field()
      field.getValue() shouldBe ""
      field.getPosition() shouldBe 0
      field.getIsOccupied() shouldBe false
      field.getPiece() shouldBe None
      field.getIsStartField() shouldBe false
      field.getIsHouseField() shouldBe false
    }

    "set and get its occupancy state correctly" in {
      val field = Field()
      field.setIsOccupied(true)
      field.getIsOccupied() shouldBe true

      field.setIsOccupied(false)
      field.getIsOccupied() shouldBe false
    }

    "set and retrieve a piece correctly" in {
      val field = Field()
      val player = Player(1, "TestPlayer")  // Create a sample Player
      val piece = Piece(player, 1)          // Create an actual Piece
      field.setPiece(Some(piece))
      field.getPiece() shouldBe Some(piece)

      field.setPiece(None)
      field.getPiece() shouldBe None
    }

    "initialize as a start field with correct properties" in {
      val field = Field()
      field.inInitializeField(5, FieldType.START)

      field.getValue() shouldBe "ST"
      field.getPosition() shouldBe 5
      field.getIsOccupied() shouldBe false
      field.getPiece() shouldBe None
      field.getIsStartField() shouldBe true
      field.getIsHouseField() shouldBe false
    }

    "initialize as a game field with correct properties" in {
      val field = Field()
      field.inInitializeField(10, FieldType.GAME)

      field.getValue() shouldBe "00"
      field.getPosition() shouldBe 10
      field.getIsOccupied() shouldBe false
      field.getPiece() shouldBe None
      field.getIsStartField() shouldBe false
      field.getIsHouseField() shouldBe false
    }

    "initialize as a home field with correct properties" in {
      val field = Field()
      field.inInitializeField(15, FieldType.HOME)

      field.getValue() shouldBe "00"
      field.getPosition() shouldBe 15
      field.getIsOccupied() shouldBe false
      field.getPiece() shouldBe None
      field.getIsStartField() shouldBe false
      field.getIsHouseField() shouldBe true
    }

    "initialize as a start house field with correct properties" in {
      val field = Field()
      field.inInitializeField(-1, FieldType.STARTHOUSE)

      field.getValue() shouldBe "00"
      field.getPosition() shouldBe -1
      field.getIsOccupied() shouldBe false
      field.getPiece() shouldBe None
      field.getIsStartField() shouldBe false
      field.getIsHouseField() shouldBe false
    }

    "set and get the house field state correctly" in {
      val field = Field()
      field.setIsHouseField(true)
      field.getIsHouseField() shouldBe true

      field.setIsHouseField(false)
      field.getIsHouseField() shouldBe false
    }
  }
}
