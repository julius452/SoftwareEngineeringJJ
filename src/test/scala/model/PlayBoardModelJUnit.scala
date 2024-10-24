package model

import org.junit.Assert._
import org.junit.Test

class PlayBoardModelJUnit {
  val expectedHouse: Array[String] = Array.fill(4)("HH")

  @Test
  def testInitializePlayboard(): Unit = {
    val model = new PlayboardModel
    model.initializePlayboard()

    val expectedPlayboard = Array.fill(40)("00")
    expectedPlayboard(0) = "ST"
    expectedPlayboard(10) = "ST"
    expectedPlayboard(20) = "ST"
    expectedPlayboard(30) = "ST"

    assertArrayEquals(expectedPlayboard.asInstanceOf[Array[AnyRef]], model.getPlayboard.asInstanceOf[Array[AnyRef]])
  }

  @Test
  def testGetHouse1(): Unit = {
    val model = new PlayboardModel

    assertArrayEquals(expectedHouse.asInstanceOf[Array[AnyRef]], model.getHouse1.asInstanceOf[Array[AnyRef]])
  }

  @Test
  def testGetHouse2(): Unit = {
    val model = new PlayboardModel

    assertArrayEquals(expectedHouse.asInstanceOf[Array[AnyRef]], model.getHouse2.asInstanceOf[Array[AnyRef]])
  }

  @Test
  def testGetHouse3(): Unit = {
    val model = new PlayboardModel

    assertArrayEquals(expectedHouse.asInstanceOf[Array[AnyRef]], model.getHouse3.asInstanceOf[Array[AnyRef]])
  }

  @Test
  def testGetHouse4(): Unit = {
    val model = new PlayboardModel

    assertArrayEquals(expectedHouse.asInstanceOf[Array[AnyRef]], model.getHouse4.asInstanceOf[Array[AnyRef]])
  }
}
