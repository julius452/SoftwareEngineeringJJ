package controller

import model.{Field, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach

class PlayerControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  "A PlayerController" should {
    val playerController = new PlayerController()
    val pieceController = new PieceController()

    "initialize a player with the correct attributes" in {
      val player = playerController.initializePlayer(1, "Player1")

      player.id shouldBe "A"
      player.name shouldBe "Player1"
      player.pieces.length shouldBe 4
      player.startHouse.length shouldBe 4
      player.house.length shouldBe 4
      player.startPosition shouldBe 0

      // Check that pieces are correctly assigned to the player's startHouse
      for (i <- 0 until 4) {
        player.startHouse(i).piece shouldBe Some(player.pieces(i))
        player.startHouse(i).isOccupied shouldBe true
        player.pieces(i).field shouldBe player.startHouse(i)
      }
    }

    "check if all pieces are off the field correctly" in {
      val player = playerController.initializePlayer(1, "Player1")

      // By default, all pieces should be off the field
      playerController.checkIfAllPiecesOffField(player) shouldBe true

      // Place one piece on the field
      player.pieces(0).isOnField = true
      playerController.checkIfAllPiecesOffField(player) shouldBe false

      // Reset piece off the field
      player.pieces(0).isOnField = false
      playerController.checkIfAllPiecesOffField(player) shouldBe true
    }
  }
}
