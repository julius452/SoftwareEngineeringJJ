package view

import builder.GameStateBuilder
import model.{Field, FieldType, GameBoard, GameState, Piece, Player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConsoleViewSpec extends AnyFlatSpec with Matchers {

  "ConsoleView" should "display start phase correctly" in {
    val expected = "Willkommen bei Mensch Ärgere Dich Nicht!\nBitte geben Sie die Anzahl der Spieler ein (2-4):"
    ConsoleView.displayStartPhase() should be(expected)
  }

  it should "display player setup phase correctly" in {
    val index = 1
    val expected = s"Name des Spielers $index:"
    ConsoleView.displayPlayerSetupPhase(index) should be(expected)
  }

  it should "display determine start player phase correctly" in {
    val player = new Player(1, "Alice")
    player.initializeHousesAndPieces()

    val expected = s"${ConsoleView.displayDivider()}Der Startspieler wird ermittelt.\n${ConsoleView.displayPleaseRoll(player)}"
    //ConsoleView.displayDetermineStartPlayerPhase(player) should be(expected)
  }

  it should "display dice roll correctly" in {
    val name = "Player1"
    ConsoleView.displayDiceRoll(6, name) should be(s"Yeah!, $name hat eine 6 gewürfelt")
    ConsoleView.displayDiceRoll(3, name) should be(s"$name hat eine 3 gewürfelt.\n")
  }

  it should "display in-game phase string correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Player1")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Player1")
    gameState.updateCurrentPlayer(player)

    val expected = s"${ConsoleView.displayDivider()}Player1 ist am Zug.\n${ConsoleView.getGameBoardAsString(gameState)}${ConsoleView.getPlayerHouseAsString(gameState)}${ConsoleView.getPlayerStartHouseAsString(gameState)}\n\nPlayer1, bitte würfeln (w):"
    ConsoleView.displayInGamePhaseString(gameState) should be(expected)
  }

  it should "display happy with players correctly" in {
    ConsoleView.displayHappyWithPlayers() should be("Sind Sie mit den Spielernamen zufrieden? (j/n):")
  }

  it should "display players correctly" in {
    val player1 = new Player(1, "Player1")
    player1.initializeHousesAndPieces()

    val player2 = new Player(2, "Player2")
    player2.initializeHousesAndPieces()

    val players = List(player1, player2)
    val expected = "\nSpieler:\n\tSpieler 1: Player1\n\tSpieler 2: Player2\n"
    ConsoleView.displayPlayers(players) should be(expected)
  }

  it should "display change player correctly" in {
    ConsoleView.displayChangePlayer() should be("Welchen Spieler möchten Sie ändern?:")
  }

  it should "display ask for new player name correctly" in {
    val playerNumber = 1
    ConsoleView.displayAskForNewPlayerName(playerNumber) should be(s"Neuer Name für Spieler $playerNumber:")
  }

  it should "display player won correctly" in {
    val player = new Player(1, "Player1")
    player.initializeHousesAndPieces()

    ConsoleView.displayPlayerWon(player) should be(s"\nGlückwunsch ${player.getPlayerName()} Du hat gewonnen!")
  }

  it should "display player can enter piece correctly" in {
    ConsoleView.displayPlayerCanEnterPiece() should be("\nDeine Möglichen Züge:\n")
  }

  it should "display which piece to move correctly" in {
    ConsoleView.displayWhichPieceToMove() should be("Welche Figur soll ziehen?:")
  }

  it should "display valid move correctly" in {
    val player = new Player(1, "Player1")
    player.initializeHousesAndPieces()

    val piece = new Piece(player, 1)
    piece.setIsOnField(true)

    val field = Field()
    field.inInitializeField(10, FieldType.GAME)

    piece.setField(field)
    ConsoleView.displayValideMove(piece) should be(s"\tFigur ${player.getPlayerId() + piece.id} (${piece.id}) auf Feld ${piece.getField().getPosition()} kann ziehen.\n")
  }

  it should "display game board correctly" in {
    val gameState = new GameStateBuilder()
      .buildDice()
      .buildGameBoard()
      .build()

    val player = Player(1, "Alice")
    player.initializeHousesAndPieces()

    gameState.setPlayersCount(1)
    gameState.addPlayer("Alice")
    gameState.updateCurrentPlayer(player)

    val expected = s"${ConsoleView.getGameBoardAsString(gameState)}${ConsoleView.getPlayerHouseAsString(gameState)}${ConsoleView.getPlayerStartHouseAsString(gameState)}\n"
    ConsoleView.displayGameBoard(gameState) should be(expected)
  }

  it should "display divider correctly" in {
    ConsoleView.displayDivider() should be("-" * 70 + "\n")
  }

  it should "display no valid moves correctly" in {
    ConsoleView.displaNoValidMoves() should be("Keine gültigen Züge für den Spieler.")
  }

  it should "display next move correctly" in {
    ConsoleView.displayNextMove() should be("Der nächste Zug wird ausgeführt.")
  }

  it should "display undo option correctly" in {
    ConsoleView.displayUndoOption() should be("Möchten Sie den Zug rückgängig machen? Drücken Sie (u) für Ja, oder eine andere Taste für Nein.")
  }

  it should "display redo option correctly" in {
    ConsoleView.displayRedoOption() should be("Möchten Sie den letzten Zug wiederherstellen? Drücken Sie (r) für Ja, oder eine andere Taste für Nein.")
  }

  it should "display error undo correctly" in {
    ConsoleView.displayErrorUndo() should be("Kein Zug zum Rückgängig machen.")
  }

  it should "display error redo correctly" in {
    ConsoleView.displayErrorRedo() should be("Kein Zug zum Wiederholen.")
  }

  it should "display error correctly" in {
    ConsoleView.displayError() should be("Fehler beim Wiederholen des Befehls: ${exception.getMessage}")
  }
}