package controller

import model.Field

class FieldController {
  def initializeStartField(position: Int): Field = {
    val value = "ST"
    val position = position
    val isOccupied = false
    val piece = None
    val isStartField = true
    val isHouseField = false

    val field = Field(
      value,
      position,
      isOccupied,
      piece,
      isStartField,
      isHouseField
    )

    return field
  }

  def initializeGameField(position: Int): Field = {
    val value = "00"
    val position = position
    val isOccupied = false
    val piece = None
    val isStartField = false
    val isHouseField = false

    val field = Field(
      value,
      position,
      isOccupied,
      piece,
      isStartField,
      isHouseField
    )

    return field
  }

  def initializeHomeField(position: Int): Field = {
    val value = "00"
    val position = position
    val isOccupied = false
    val piece = None
    val isStartField = false
    val isHouseField = true

    val field = Field(
      value,
      position,
      isOccupied,
      piece,
      isStartField,
      isHouseField
    )

    return field
  }
}
