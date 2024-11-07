package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {

  "A Field" should {
    "initialize with correct attributes" in {
      val field = Field("ST", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false)
      field.value shouldBe "ST"
      field.position shouldBe 0
      field.isOccupied shouldBe false
      field.piece shouldBe None
      field.isStartField shouldBe true
      field.isHouseField shouldBe false
    }

    "allow changing occupation status and piece" in {
      val field = Field("00", 1, isOccupied = false, piece = None, isStartField = false, isHouseField = false)
      field.isOccupied = true
      field.isOccupied shouldBe true

      val player = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val piece = Piece(player, 1, 0, isInHome = false, isOnField = true, field)
      field.piece = Some(piece)

      field.piece shouldBe Some(piece)
    }
  }
}