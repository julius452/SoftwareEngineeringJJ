package View

object PlayboardView {
  // Funktion zur Umwandlung in ein 2D-Layout und Ausgabe
  def printBoard(spielfeld: Array[String], house1: Array[String], house2: Array[String], house3: Array[String], house4: Array[String]): Unit = {
    println("Herzlich Willkommen bei unserem tollen Mensch ärgere dich nicht")
    println("Hier ist ein erster Entwurf unseres Spielfeldes:\n")

    // Ausgabe des Spielfelds
    println(spielfeld.mkString(" | "))

    println("\nHäuser der Spieler:")
    println("Player1 House: " + house1.mkString(" | "))
    println("Player2 House: " + house2.mkString(" | "))
    println("Player3 House: " + house3.mkString(" | "))
    println("Player4 House: " + house4.mkString(" | "))
  }
}
