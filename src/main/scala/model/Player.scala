package model

case class Player(
  id: String,
  name: String,
  pieces: Array[Piece],
  house: Array[Field],
  startHouse: Array[Field],
  startPosition: Int
)

