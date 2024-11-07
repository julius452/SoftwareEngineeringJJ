package controller

import model.{Field, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach

class PieceControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  "A PieceController" should {
    val pieceController = new PieceController()

    "initialize a piece with the correct attributes" in {
      val player = new Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
      val piece = pieceController.initializePiece(player, 1)

      piece.player shouldBe player
      piece.id shouldBe 1
      piece.traveledFields shouldBe 0
      piece.isInHome shouldBe false
      piece.isOnField shouldBe false
      piece.field shouldBe player.startHouse(0) // Piece should be in the first slot of startHouse
    }
  }
}