package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameBoardSpec extends AnyWordSpec with Matchers {

  "A GameBoard" should {
    "initialize with an array of fields" in {
      val fields = Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
      val gameBoard = GameBoard(fields)

      gameBoard.fields.length shouldBe 40
      gameBoard.fields(0).isStartField shouldBe false
    }
  }
}

