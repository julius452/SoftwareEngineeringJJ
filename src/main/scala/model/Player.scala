package model

case class Player(playerId: Int, nameString: String) {
  private val playerIdAsString = Array("A", "B", "C", "D")

  def id: String = playerIdAsString(playerId - 1)
  def name: String = nameString

  private var startHouse: Array[Field] = new Array[Field](4)

  private var pieces: Array[Piece] = new Array[Piece](4)

  private var house: Array[Field] = new Array[Field](4)
  def startPosition: Int = (playerId - 1) * 10

  def getStartHouse(): Array[Field] = startHouse
  def getHouse(): Array[Field] = house
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
}
