package view


import model.{Dice, Field, GameBoard, GameState, Piece, Player}
import controller.FieldController
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ConsoleViewSpec extends AnyWordSpec with Matchers {

  "A ConsoleView" should {
    val consoleView = new ConsoleView()
    val fieldController = new FieldController()

    val houses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = true))
    for (i <- houses.indices) {
      houses(i) = fieldController.initializeHomeField(i)
    }

    val pieces = Array.ofDim[Piece](4)
    val startHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false))

    for (i <- pieces.indices) {
      pieces(i) = Piece(Player("A", "Player1", pieces, Array(), Array(), startPosition = 0), i + 1, traveledFields = 0, isInHome = false, isOnField = false, startHouses(i))
      startHouses(i).piece = Some(pieces(i))
      startHouses(i).isOccupied = true
    }

    val player = Player("A", "Player1", pieces, houses, startHouses, startPosition = 0)
    val piece = Piece(player, 1, traveledFields = 0, isInHome = false, isOnField = true, Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false))
    val gameBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
    val gameState = GameState(List(player), player, Dice(6), gameBoard, isRunning = true)

    "display a prompt to ask for player count" in {
      consoleView.displayAskForPlayersCount() shouldBe "Wie viele spielen mit? (2-4):"
    }

    "display a prompt to ask for player name" in {
      consoleView.displayAskForPlayerName(1) shouldBe "Name des Spielers 1:"
    }

    "display a message to determine starting player" in {
      consoleView.displayDetermineStartingPlayer() shouldBe "Um den Startspieler zu bestimmen, müssen alle Spieler einmal würfeln:"
    }

    "display a prompt asking player to roll" in {
      consoleView.displayAskPlayerToRoll(player) shouldBe "Player1, bitte würfeln (w):"
    }

    "display the dice roll result" in {
      consoleView.displayDiceRollResult(player, 4) shouldBe "Player1 hat eine 4 gewürfelt.\n"
    }

    "display the starting player" in {
      consoleView.displayStartPlayer(player) shouldBe "Player1 beginnt!"
    }

    "display the turn information for the current player" in {
      consoleView.displayTurnInfo(player) shouldBe "Player1 ist am Zug."
    }

    "display a warning for wrong input" in {
      consoleView.displayWrongInput() shouldBe "\nFalsche Eingabe!! Nochmal versuchen:\n"
    }

    "display a winning message for the player" in {
      consoleView.displayPlayerWon(player) shouldBe "\nGlückwunsch Player1 Du hat gewonnen!"
    }

    "display a message for player to enter a piece" in {
      consoleView.displayPlayerCanEnterPiece(player) shouldBe "\nMögliche Züge:."
    }

    "display a prompt to ask which piece to move" in {
      consoleView.displayWhichPieceToMove() shouldBe "Welche Figur soll ziehen?:"
    }

    "display a valid move for a piece" in {
      piece.isOnField = true
      piece.field = Field("00", 3, isOccupied = true, piece = Some(piece), isStartField = false, isHouseField = false)
      consoleView.displayValideMove(piece) shouldBe "\tFigur A1 (1) auf Feld 3 kann ziehen."

      piece.isOnField = false
      piece.isInHome = true
      piece.field = Field("00", 0, isOccupied = true, piece = Some(piece), isStartField = false, isHouseField = true)
      consoleView.displayValideMove(piece) shouldBe "\tFigur A1 (1) kann im Haus ziehen."

      piece.isOnField = false
      piece.isInHome = false
      piece.field = Field("00", 0, isOccupied = true, piece = Some(piece), isStartField = false, isHouseField = false)
      consoleView.displayValideMove(piece) shouldBe "\tFigur A1 (1) kann auf das Spielfeld gesetzt werden."
    }

    "display the game board" in {
      // Test with all fields unoccupied
      val unoccupiedBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      val unoccupiedHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = true))
      val unoccupiedStartHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false))
      val unoccupiedPlayer = Player("A", "Player1", pieces, unoccupiedHouses, unoccupiedStartHouses, startPosition = 0)
      val unoccupiedGameState = GameState(List(unoccupiedPlayer), unoccupiedPlayer, Dice(6), unoccupiedBoard, isRunning = true)
      val unoccupiedBoardString = consoleView.getGameBoardAsString(unoccupiedGameState) +
        consoleView.getPlayerHouseAsString(unoccupiedGameState) +
        consoleView.getPlayerStartHouseAsString(unoccupiedGameState)
      consoleView.displayGameBoard(unoccupiedGameState) shouldBe unoccupiedBoardString

      // Test with some fields occupied
      val occupiedBoard = GameBoard(Array.tabulate(40)(i => Field("00", i, isOccupied = i % 2 == 0, piece = if (i % 2 == 0) Some(piece) else None, isStartField = false, isHouseField = false)))
      val occupiedHouses = Array.tabulate(4)(i => Field("00", i, isOccupied = i % 2 == 0, piece = if (i % 2 == 0) Some(piece) else None, isStartField = false, isHouseField = true))
      val occupiedStartHouses = Array.tabulate(4)(i => Field("00", i, isOccupied = i % 2 == 0, piece = if (i % 2 == 0) Some(piece) else None, isStartField = true, isHouseField = false))
      val occupiedPlayer = Player("A", "Player1", pieces, occupiedHouses, occupiedStartHouses, startPosition = 0)
      val occupiedGameState = GameState(List(occupiedPlayer), occupiedPlayer, Dice(6), occupiedBoard, isRunning = true)
      val occupiedBoardString = consoleView.getGameBoardAsString(occupiedGameState) +
        consoleView.getPlayerHouseAsString(occupiedGameState) +
        consoleView.getPlayerStartHouseAsString(occupiedGameState)
      consoleView.displayGameBoard(occupiedGameState) shouldBe occupiedBoardString
    }

    "display the game board as a string" in {
      // Test with all fields unoccupied
      val unoccupiedBoard = GameBoard(Array.fill(40)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = false)))
      val unoccupiedGameState = GameState(List(player), player, Dice(6), unoccupiedBoard, isRunning = true)
      val unoccupiedBoardString = consoleView.getGameBoardAsString(unoccupiedGameState)
      unoccupiedBoardString should include ("Spielfeld:")
      unoccupiedBoardString should include ("00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00 | 00")

      // Test with some fields occupied
      val occupiedBoard = GameBoard(Array.tabulate(40)(i => Field("00", i, isOccupied = i % 2 == 0, piece = if (i % 2 == 0) Some(piece) else None, isStartField = false, isHouseField = false)))
      val occupiedGameState = GameState(List(player), player, Dice(6), occupiedBoard, isRunning = true)
      val occupiedBoardString = consoleView.getGameBoardAsString(occupiedGameState)
      occupiedBoardString should include ("Spielfeld:")
      occupiedBoardString should include ("A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00 | A1 | 00")

    }

    "display the player house as a string" in {
      // Test with all house fields unoccupied
      val unoccupiedHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = false, isHouseField = true))
      val unoccupiedPlayer = Player("A", "Player1", pieces, unoccupiedHouses, startHouses, startPosition = 0)
      val unoccupiedGameState = GameState(List(unoccupiedPlayer), unoccupiedPlayer, Dice(6), gameBoard, isRunning = true)
      val unoccupiedHouseString = consoleView.getPlayerHouseAsString(unoccupiedGameState)
      unoccupiedHouseString should include ("Haus:")
      unoccupiedHouseString should include ("00 | 00 | 00 | 00")

      // Test with some house fields occupied
      val occupiedHouses = Array.tabulate(4)(i => Field("00", i, isOccupied = i % 2 == 0, piece = if (i % 2 == 0) Some(piece) else None, isStartField = false, isHouseField = true))
      val occupiedPlayer = Player("A", "Player1", pieces, occupiedHouses, startHouses, startPosition = 0)
      val occupiedGameState = GameState(List(occupiedPlayer), occupiedPlayer, Dice(6), gameBoard, isRunning = true)
      val occupiedHouseString = consoleView.getPlayerHouseAsString(occupiedGameState)
      occupiedHouseString should include ("Haus:")
      occupiedHouseString should include ("A1 | 00 | A1 | 00")
    }

    "display the player's start house as a string" in {
      // Test with all start house fields unoccupied
      val unoccupiedStartHouses = Array.fill(4)(Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false))
      val unoccupiedPlayer = Player("A", "Player1", pieces, houses, unoccupiedStartHouses, startPosition = 0)
      val unoccupiedGameState = GameState(List(unoccupiedPlayer), unoccupiedPlayer, Dice(6), gameBoard, isRunning = true)
      val unoccupiedStartHouseString = consoleView.getPlayerStartHouseAsString(unoccupiedGameState)
      unoccupiedStartHouseString should include ("Starthäuschen:")
      unoccupiedStartHouseString should include ("00 | 00 | 00 | 00")

      // Test with some start house fields occupied
      val occupiedStartHouses = Array.tabulate(4)(i => Field("00", i, isOccupied = i % 2 == 0, piece = if (i % 2 == 0) Some(piece) else None, isStartField = true, isHouseField = false))
      val occupiedPlayer = Player("A", "Player1", pieces, houses, occupiedStartHouses, startPosition = 0)
      val occupiedGameState = GameState(List(occupiedPlayer), occupiedPlayer, Dice(6), gameBoard, isRunning = true)
      val occupiedStartHouseString = consoleView.getPlayerStartHouseAsString(occupiedGameState)
      occupiedStartHouseString should include ("Starthäuschen:")
      occupiedStartHouseString should include ("A1 | 00 | A1 | 00")
    }

    "display a message if the player rolls a 6 and can roll again" in {
      consoleView.displayPlayerCanRollAgain(player) shouldBe "\nPlayer1 hat eine 6 gewürfelt und darf nochmal würfeln."
    }

    "display a divider" in {
      consoleView.displayDivider() shouldBe "\n" + "-" * 70 + "\n"
    }
  }
}