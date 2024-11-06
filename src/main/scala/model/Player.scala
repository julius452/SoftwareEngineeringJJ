package model

case class Player(
  id: String,
  name: String,
  pieces: Array[Piece],
  house: Array[String], // Maybe model Field
  startPosition: Int
)

