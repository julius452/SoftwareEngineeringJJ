package model

import memento.PlayerMemento

case class Player(playerId: Int, nameString: String){

  private val playerIdAsString = Array("A", "B", "C", "D")

  private var id: String = playerIdAsString(playerId - 1)

  private var number: Int = playerId

  private var name: String = nameString

  private var startHouse: Array[Field] = new Array[Field](4)

  private var pieces: Array[Piece] = new Array[Piece](4)

  private var house: Array[Field] = new Array[Field](4)

  private var startPosition: Int = (playerId - 1) * 10

  def setPlayerName(playerName: String): Unit = {
    this.name = playerName
  }

  def getStartPosition(): Int = {
    this.startPosition
  }

  def getPlayerNumber(): Int = number

  def getPlayerId(): String = id

  def getPlayerName(): String = name

  def getStartHouse(): Array[Field] = startHouse
  def getHouse(): Array[Field] = house
  def setHouse(house: Array[Field]): Unit = this.house = house
  def getPieces(): Array[Piece] = pieces

  def initializeHousesAndPieces(): Unit = {
    for (i <- 0 to 3) {
      pieces(i) = Piece(this, i + 1)
      startHouse(i) = Field()
      startHouse(i).inInitializeField(i, FieldType.STARTHOUSE)

      startHouse(i).setPiece(Some(pieces(i)))
      startHouse(i).setIsOccupied(true)

      pieces(i).setField(startHouse(i))
    }

    for (i <- 0 to 3) {
      house(i) = Field()
      house(i).inInitializeField(i, FieldType.HOME)
    }
  }

  def checkIfAllPiecesOffField(): Boolean = {
    for (piece <- pieces) {
      if (piece.getIsOnField()) {
        return false
      }
    }

    true
  }

  // Zustand speichern
  def save(): PlayerMemento = new PlayerMemento(name)

  // Zustand wiederherstellen
  def restore(memento: PlayerMemento): Unit = {
    name = memento.getName()
  }
}

object Player {
  def checkNumberOfPlayers(number: Int): Boolean = {
    if (number < 2 || number > 4) {
      return false
    }
    true
  }
}
