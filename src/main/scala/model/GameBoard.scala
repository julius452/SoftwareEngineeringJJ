package model

case class GameBoard() {
  private var fields: Array[Field] = new Array[Field](40)

  def initializeGameBoard(): Unit = {
    for (i <- 0 until 40) {
      fields(i) = Field()

      if (i % 10 == 0) {
        fields(i).inInitializeField(i, FieldType.START)
      } else {
        fields(i).inInitializeField(i, FieldType.GAME)
      }
    }
  }

  def getFields(): Array[Field] = fields
}

