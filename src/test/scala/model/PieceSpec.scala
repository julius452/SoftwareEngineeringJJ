import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.{Piece, Player, Field}

class PieceSpec extends AnyWordSpec with Matchers {

  "A Piece" should {

    "initialize with correct default values" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)

      piece.getTraveledFields() shouldBe 0
      piece.getIsInHome() shouldBe false
      piece.getIsOnField() shouldBe false
      piece.getField().getPosition() shouldBe 0 // The field should be the default Field with position 0
    }

    "set and get traveled fields correctly" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)

      piece.setTravelFields(10)
      piece.getTraveledFields() shouldBe 10

      piece.setTravelFields(20)
      piece.getTraveledFields() shouldBe 20
    }

    "set and get isInHome state correctly" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)

      piece.setIsInHome(true)
      piece.getIsInHome() shouldBe true

      piece.setIsInHome(false)
      piece.getIsInHome() shouldBe false
    }

    "set and get isOnField state correctly" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)

      piece.setIsOnField(true)
      piece.getIsOnField() shouldBe true

      piece.setIsOnField(false)
      piece.getIsOnField() shouldBe false
    }

    "set and get the field correctly" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)
      val field = Field()

      piece.setField(field)
      piece.getField() shouldBe field
    }
  }
}
