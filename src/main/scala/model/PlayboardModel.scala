package model

class PlayboardModel {
  // Das Spielfeld und die Häuser, die für jede Instanz unterschiedlich sein können
  val playboard: Array[String] = Array.fill(40)("00")
  val House1, House2, House3, House4: Array[String] = Array.fill(4)("HH")

  def initializePlayboard(): Unit = {
    playboard(0) = "ST"
    playboard(10) = "ST"
    playboard(20) = "ST"
    playboard(30) = "ST"
  }

  def getPlayboard: Array[String] = playboard
  def getHouse1: Array[String] = House1
  def getHouse2: Array[String] = House2
  def getHouse3: Array[String] = House3
  def getHouse4: Array[String] = House4
}
