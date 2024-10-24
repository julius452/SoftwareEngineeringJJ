package view

object PlayboardView {
  // Funktion zur Umwandlung in ein 2D-Layout und Ausgabe
  def printBoard(spielfeld: Array[String], house1: Array[String], house2: Array[String], house3: Array[String], house4: Array[String]): String = {
    val builder = new StringBuilder()
    builder.append(printMessage())
    builder.append(printPlayBoard(spielfeld))
    builder.append(printHouses(house1, house2, house3, house4))
    builder.mkString
  }

  def printMessage(): String = {
    val builder = new StringBuilder()
    builder.append("Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht")
    builder.append("\n")
    builder.append("Hier ist ein erster Entwurf unseres Spielfeldes:")
    builder.append("\n")
    builder.append("\n")
    builder.mkString
  }

  def printPlayBoard(spielfeld: Array[String]): String = {
    spielfeld.mkString(" | ")
  }

  def printHouses(house1: Array[String], house2: Array[String], house3: Array[String], house4: Array[String]): String = {
    val builder = new StringBuilder()
    builder.append("\n")
    builder.append("\nHäuser der Spieler:")
    builder.append("\nPlayer1 House: " + house1.mkString(" | "))
    builder.append("\nPlayer2 House: " + house2.mkString(" | "))
    builder.append("\nPlayer3 House: " + house3.mkString(" | "))
    builder.append("\nPlayer4 House: " + house4.mkString(" | "))
    builder.mkString
  }
}
