package model

case class Field(
  value: String,
  position: Int,
  var isOccupied: Boolean,
  var piece: Option[Piece],
  isStartField: Boolean,
  isHouseField: Boolean
)
