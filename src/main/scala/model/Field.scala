package model

import model.FieldType.FieldType

case class Field() {
  private var value: String = ""
  private var position: Int = 0
  private var isOccupied: Boolean = false
  private var piece: Option[Piece] = None
  private var isStartField: Boolean = false
  private var isHouseField: Boolean = false

  def getValue(): String = value

  def getPosition(): Int = position

  def setIsOccupied(newIsOccupied: Boolean): Unit = {
    isOccupied = newIsOccupied
  }
  def getIsOccupied(): Boolean = isOccupied

  def setPiece(newPiece: Option[Piece]): Unit = {
    piece = newPiece
  }
  def getPiece(): Option[Piece] = piece

  def getIsStartField(): Boolean = isStartField

  def setIsHouseField(newIsHouseField: Boolean): Unit = {
    isHouseField = newIsHouseField
  }
  def getIsHouseField(): Boolean = isHouseField

  def inInitializeField(positionIndex: Int, fieldType: FieldType): Unit = {
    fieldType match {
      case FieldType.START => initializeStartField(positionIndex)
      case FieldType.GAME => initializeGameField(positionIndex)
      case FieldType.HOME => initializeHomeField(positionIndex)
      case FieldType.STARTHOUSE => initializeStartHouseField()
    }
  }

  def initializeStartField(positionIndex: Int): Unit = {
    value = "ST"
    position = positionIndex
    isOccupied = false
    piece = None
    isStartField = true
    isHouseField = false
  }

  def initializeGameField(positionIndex: Int): Unit = {
    value = "00"
    position = positionIndex
    isOccupied = false
    piece = None
    isStartField = false
    isHouseField = false
  }

  def initializeHomeField(positionIndex: Int): Unit = {
    value = "00"
    position = positionIndex
    isOccupied = false
    piece = None
    isStartField = false
    isHouseField = true
  }

  def initializeStartHouseField(): Unit = {
    value = "00"
    position = -1
    isOccupied = false
    piece = None
    isStartField = false
    isHouseField = false
  }
}
