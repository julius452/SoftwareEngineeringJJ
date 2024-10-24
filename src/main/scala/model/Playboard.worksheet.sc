// Das Spielfeld mit 40 Feldern initialisieren
val spielfeld: Array[String] = Array.fill(40)("00")

// Häuser der Spieler (jeweils 4 Felder pro Spieler)
val House1, House2, House3, House4: Array[String] = Array.fill(4)("HH")

// Beispielbelegung der Felder
spielfeld(0) = "ST"   // Spieler A auf Feld 0
spielfeld(10) = "ST"  // Spieler B auf Feld 10
spielfeld(20) = "ST"  // Spieler C auf Feld 20
spielfeld(30) = "ST"  // Spieler D auf Feld 30

spielfeld(14) = "A2"
spielfeld(21) = "B1"
spielfeld(31) = "C1"
spielfeld(32) = "E1"

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

printBoard(spielfeld, House1, House2, House3, House4) //Liste an Häusern muss noch übergeben werden


