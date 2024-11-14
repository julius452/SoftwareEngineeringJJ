package model

case class Piece(playerOfPiece: Player, pieceId: Int) {
  def player: Player = playerOfPiece
  def id: Int = pieceId

  private var traveledFields: Int = 0

  private var isInHome: Boolean = false

  private var isOnField: Boolean = false

  private var field: Field = Field()

  def setTravelFields(traveledFields: Int): Unit = {
    this.traveledFields = traveledFields
  }
  def getTraveledFields(): Int = traveledFields

  def setIsInHome(isInHome: Boolean): Unit = {
    this.isInHome = isInHome
  }
  def getIsInHome(): Boolean = isInHome

  def setIsOnField(isOnField: Boolean): Unit = {
    this.isOnField = isOnField
  }
  def getIsOnField(): Boolean = isOnField

  def setField(newField: Field): Unit = {
    field = newField
  }
  def getField(): Field = field
}

