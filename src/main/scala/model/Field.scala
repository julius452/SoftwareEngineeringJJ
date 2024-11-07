package model

case class Field(
  value: String,
  position: Int,
  isOccupied: Boolean,
  piece: Option[Piece],
  isStartField: Boolean,
  isHouseField: Boolean
)
