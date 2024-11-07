package view


import model.{Dice, Field, GameBoard, GameState, Piece, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ConsoleViewSpec extends AnyWordSpec with Matchers {

  "A ConsoleView" should {
    val consoleView = new ConsoleView()
    val player = Player("A", "Player1", Array(), Array(), Array(), startPosition = 0)
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
    }

    "display the game board as a string" in {
      val boardString = consoleView.getGameBoardAsString(gameState)
      boardString should include ("Spielfeld:")
    }

    "display the player house as a string" in {
      val houseString = consoleView.getPlayerHouseAsString(gameState)
      houseString should include ("Haus:")
    }

    "display the player's start house as a string" in {
      val startHouseString = consoleView.getPlayerStartHouseAsString(gameState)
      startHouseString should include ("Starthäuschen:")
    }

    "display a message if the player rolls a 6 and can roll again" in {
      consoleView.displayPlayerCanRollAgain(player) shouldBe "\nPlayer1 hat eine 6 gewürfelt und darf nochmal würfeln."
    }

    "display a divider" in {
      consoleView.displayDivider() shouldBe "\n" + "-" * 70 + "\n"
    }
  }
}