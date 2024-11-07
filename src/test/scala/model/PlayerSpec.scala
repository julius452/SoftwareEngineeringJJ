package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    val fields = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
    val pieces = Array.fill(4)(Piece(null, 0, 0, isInHome = false, isOnField = false, fields(0)))

    "initialize with correct attributes" in {
      val player = Player("A", "Player1", pieces, fields, fields, startPosition = 0)

      player.id shouldBe "A"
      player.name shouldBe "Player1"
      player.pieces.length shouldBe 4
      player.house.length shouldBe 4
      player.startHouse.length shouldBe 4
      player.startPosition shouldBe 0
    }

    "allow updating piece positions in the house and startHouse" in {
      val player = Player("A", "Player1", pieces, fields, fields, startPosition = 0)

      // Setting one piece in the start house and marking it as occupied
      val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = true, fields(0))
      player.startHouse(0) = fields(0)
      player.startHouse(0).piece = Some(piece)
      player.startHouse(0).isOccupied = true

      player.startHouse(0).piece shouldBe Some(piece)
      player.startHouse(0).isOccupied shouldBe true
    }
  }
}