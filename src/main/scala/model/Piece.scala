package model

case class Piece(
  player: Player,
  id: Int,
  var traveledFields: Int,
  var isInHome: Boolean,
  var isOnField: Boolean,
  var field: Field
)

