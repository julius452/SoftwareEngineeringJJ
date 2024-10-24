package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayboardModelSpec extends AnyWordSpec {

  "PlayboardModel" should {
    "initialize the playboard with start positions" in {
      val model = new PlayboardModel()
      model.initializePlayboard()
      model.getPlayboard(0) should be ("ST")
      model.getPlayboard(10) should be ("ST")
      model.getPlayboard(20) should be ("ST")
      model.getPlayboard(30) should be ("ST")
    }

    "have all houses initialized with 'HH'" in {
      val model = new PlayboardModel()
      model.getHouse1 should be(Array("HH", "HH", "HH", "HH"))
      model.getHouse2 should be(Array("HH", "HH", "HH", "HH"))
      model.getHouse3 should be(Array("HH", "HH", "HH", "HH"))
      model.getHouse4 should be(Array("HH", "HH", "HH", "HH"))
    }
  }
}