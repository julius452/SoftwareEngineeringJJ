package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayboardModelSpec extends AnyWordSpec {
  "PlayboardModel" should {

    "initialize the playboard correctly" in {
      val model = new PlayboardModel
      model.initializePlayboard()

      val expectedPlayboard = Array.fill(40)("00")
      expectedPlayboard(0) = "ST"
      expectedPlayboard(10) = "ST"
      expectedPlayboard(20) = "ST"
      expectedPlayboard(30) = "ST"

      model.getPlayboard should equal(expectedPlayboard)
    }

    "return the correct house arrays" in {
      val model = new PlayboardModel

      val expectedHouse = Array.fill(4)("HH")

      model.getHouse1 should equal(expectedHouse)
      model.getHouse2 should equal(expectedHouse)
      model.getHouse3 should equal(expectedHouse)
      model.getHouse4 should equal(expectedHouse)
    }
  }
}