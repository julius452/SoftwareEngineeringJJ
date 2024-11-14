import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.{GameState, GameBoard, Piece, Player}
import view.ConsoleView

class ConsoleViewSpec extends AnyWordSpec with Matchers {

  "ConsoleView" should {

    "display correct prompt for number of players" in {
      val view = new ConsoleView()
      view.displayAskForPlayersCount() shouldBe "Wie viele spielen mit? (2-4):"
    }

    "display correct prompt for player name" in {
      val view = new ConsoleView()
      view.displayAskForPlayerName(1) shouldBe "Name des Spielers 1:"
    }

    "display prompt to determine starting player" in {
      val view = new ConsoleView()
      view.displayDetermineStartingPlayer() shouldBe "Um den Startspieler zu bestimmen, müssen alle Spieler einmal würfeln:"
    }

    "prompt player to roll dice" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayAskPlayerToRoll(player) shouldBe "Player1, bitte würfeln (w):"
    }

    "display dice roll result" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayDiceRollResult(player, 5) shouldBe "Player1 hat eine 5 gewürfelt.\n"
    }

    "display the starting player" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayStartPlayer(player) shouldBe "Player1 beginnt!"
    }

    "display the turn information" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayTurnInfo(player) shouldBe "Player1 ist am Zug."
    }

    "display wrong input prompt" in {
      val view = new ConsoleView()
      view.displayWrongInput() shouldBe "\nFalsche Eingabe!! Nochmal versuchen:\n"
    }

    "display player won message" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayPlayerWon(player) shouldBe "\nGlückwunsch Player1 Du hat gewonnen!"
    }

    "display player can enter a piece" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayPlayerCanEnterPiece(player) shouldBe "\nMögliche Züge:"
    }

    "prompt for which piece to move" in {
      val view = new ConsoleView()
      view.displayWhichPieceToMove() shouldBe "Welche Figur soll ziehen?:"
    }

    "display valid move for a piece on the field" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)
      piece.setIsOnField(true)
      piece.setField(new model.Field())
      val view = new ConsoleView()
      view.displayValideMove(piece) shouldBe "\tFigur A1 (1) auf Feld 0 kann ziehen."
    }

    "display valid move for a piece in home" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)
      piece.setIsInHome(true)
      piece.setField(new model.Field())
      val view = new ConsoleView()
      view.displayValideMove(piece) shouldBe "\tFigur A1 (1) kann im Haus ziehen."
    }

    "display valid move for a piece in start house" in {
      val player = Player(1, "Player1")
      val piece = Piece(player, 1)
      piece.setIsOnField(false)
      piece.setIsInHome(false)
      piece.setField(new model.Field())
      val view = new ConsoleView()
      view.displayValideMove(piece) shouldBe "\tFigur A1 (1) kann auf das Spielfeld gesetzt werden."
    }

    "display the game board correctly" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()
      val board = GameBoard()
      board.initializeGameBoard()
      val gameState = new GameState(List(player), null, board)
      val view = new ConsoleView()
      view.displayGameBoard(gameState).contains("Spielfeld:") shouldBe true
    }

    "display the player's house correctly" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()
      val board = new GameBoard()
      board.initializeGameBoard()
      val gameState = new GameState(List(player), null, board)
      val view = new ConsoleView()
      view.getPlayerHouseAsString(gameState).contains("Haus:") shouldBe true
    }

    "display the player's start house correctly" in {
      val player = Player(1, "Player1")
      player.initializeHousesAndPieces()
      val board = new GameBoard()
      board.initializeGameBoard()
      val gameState = new GameState(List(player), null, board)
      val view = new ConsoleView()
      view.getPlayerStartHouseAsString(gameState).contains("Starthäuschen:") shouldBe true
    }

    "display player can roll again if they rolled a 6" in {
      val player = Player(1, "Player1")
      val view = new ConsoleView()
      view.displayPlayerCanRollAgain(player) shouldBe "\nPlayer1 hat eine 6 gewürfelt und darf nochmal würfeln."
    }

    "display a divider" in {
      val view = new ConsoleView()
      view.displayDivider() shouldBe "\n" + ("-" * 70) + "\n"
    }
  }
}
