package controller

import model.Field

class FieldController {
  def initializeStartField(positionIndex: Int): Field = {
    val value = "ST"
    val position = positionIndex
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

  def initializeGameField(positionIndex: Int): Field = {
    val value = "00"
    val position = positionIndex
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

  def initializeHomeField(positionIndex: Int): Field = {
    val value = "00"
    val position = positionIndex
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

  def initializeStartHouseField(): Field = {
    val value = "00"
    val position = -1
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
}
