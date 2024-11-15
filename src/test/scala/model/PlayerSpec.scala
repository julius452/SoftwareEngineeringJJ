import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.{Player, Field, Piece, FieldType}

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {

    "initialize with the correct player ID and name" in {
      val player = Player(1, "Player1")
      player.id shouldBe "A"
      player.name shouldBe "Player1"
    }

    "initialize houses and pieces correctly" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()

      // Check startHouse initialization
      player.getStartHouse().length shouldBe 4
      player.getStartHouse()(0).getPosition() shouldBe -1
      player.getStartHouse()(0).getIsOccupied() shouldBe true
      player.getStartHouse()(0).getPiece().isDefined shouldBe true

      // Check house initialization
      player.getHouse().length shouldBe 4
      player.getHouse()(0).getPosition() shouldBe 0
      player.getHouse()(0).getIsStartField() shouldBe false
      player.getHouse()(0).getIsHouseField() shouldBe true

      // Check pieces initialization
      player.getPieces().length shouldBe 4
      player.getPieces()(0).getField().getPosition() shouldBe -1
      player.getPieces()(0).getIsOnField() shouldBe false
    }

    "return the correct start position" in {
      val player = Player(1, "Player1")
      player.startPosition shouldBe 0
    }

    "check if all pieces are off the field correctly" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()

      // Initially, all pieces should not be on the field
      player.checkIfAllPiecesOffField() shouldBe true

      // Simulate moving a piece onto the field
      player.getPieces()(0).setIsOnField(true)
      player.checkIfAllPiecesOffField() shouldBe false

      // Set all pieces off the field again
      player.getPieces()(0).setIsOnField(false)
      player.getPieces()(1).setIsOnField(false)
      player.getPieces()(2).setIsOnField(false)
      player.getPieces()(3).setIsOnField(false)
      player.checkIfAllPiecesOffField() shouldBe true
    }

    "set and get house correctly" in {
      val player = Player(1, "Player1")
      val newHouse = Array.fill(4)(Field())
      player.setHouse(newHouse)

      player.getHouse() shouldBe newHouse
    }
  }
}
