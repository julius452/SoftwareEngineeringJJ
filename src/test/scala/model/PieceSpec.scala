package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PieceSpec extends AnyWordSpec with Matchers {

  "A Piece" should {
    val player = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
    val field = Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)

    "initialize with correct attributes" in {
      val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = true, field)
      piece.player shouldBe player
      piece.id shouldBe 1
      piece.traveledFields shouldBe 0
      piece.isInHome shouldBe false
      piece.isOnField shouldBe true
      piece.field shouldBe field
    }

    "update traveled fields, home status, and on-field status" in {
      val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = true, field)

      piece.traveledFields = 5
      piece.traveledFields shouldBe 5

      piece.isInHome = true
      piece.isInHome shouldBe true

      piece.isOnField = false
      piece.isOnField shouldBe false
    }
  }
}