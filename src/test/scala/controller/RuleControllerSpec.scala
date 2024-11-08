package controller

import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach

class RuleControllerSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  val ruleController = new RuleController()
  val gameBoardController = new GameBoardController()
  val gameStateController = new GameStateController()

  // Spieler-Setup
  val startField = new Field("00", 0, isOccupied = false, piece = None, isStartField = true, isHouseField = false)
  val startHouses = Array.fill(4)(startField)
  val player1 = new Player("1", "Player1", Array(), Array(), startHouses, startPosition = 0)
  val player2 = new Player("2", "Player2", Array(), Array(), startHouses, startPosition = 0)

  val gameBoard = gameBoardController.initializeGameBoard()
  val gameState = new GameState(List(player1, player2), player1, Dice(6), gameBoard, isRunning = true)

  // Spielfigur Setup
  val piece1 = new Piece(player1, 1, 0, isInHome = false, isOnField = false, gameBoard.fields(0))
  val piece2 = new Piece(player2, 2, 0, isInHome = false, isOnField = false, gameBoard.fields(1))

  "A RuleController" should {
    "check if the start field is free for the player" in {
      val isFree = ruleController.isStartFieldFree(player1, gameState)

      // Das Startfeld ist frei, also sollte true zurückgegeben werden
      isFree shouldBe true

      // Setzen wir das Startfeld von Player1 auf belegt
      gameBoard.fields(player1.startPosition).isOccupied = true
      val isFreeAfterOccupation = ruleController.isStartFieldFree(player1, gameState)

      // Jetzt ist das Startfeld besetzt, daher sollte false zurückgegeben werden
      isFreeAfterOccupation shouldBe false
    }

    "detect collision on an occupied field" in {
      val landingField = gameBoard.fields(1)
      landingField.isOccupied = true
      landingField.piece = Some(piece2)

      // Kollisionserkennung
      ruleController.checkCollision(piece1, landingField, gameState) shouldBe true
    }

    "validate a move when the piece is not on the field and dice roll is 6" in {
      piece1.isOnField = false
      gameState.dice.lastRoll = 6

      // Sicherstellen, dass das Startfeld frei ist
      val startField = gameState.board.fields(piece1.player.startPosition)
      startField.isOccupied = false  // Setze sicher, dass das Startfeld frei ist

      // Es wird geprüft, ob der Zug gültig ist, wenn die Spielfigur nicht auf dem Feld ist und eine 6 geworfen wird
      ruleController.validateMove(piece1, gameState) shouldBe true
    }

    "not allow moving a piece when the piece is not on the field and dice roll is not 6" in {
      piece1.isOnField = false
      gameState.dice.lastRoll = 4  // Kein 6er Wurf

      // Der Zug sollte nicht gültig sein, weil die Spielfigur nicht auf dem Feld ist und keine 6 geworfen wurde
      ruleController.validateMove(piece1, gameState) shouldBe false
    }

    "not allow a piece to enter the game if the start field is occupied" in {
      piece1.isOnField = false
      gameState.dice.lastRoll = 6  // 6 für den Eintritt ins Spiel

      // Setze das Startfeld von Player1 auf besetzt
      gameBoard.fields(piece1.player.startPosition).isOccupied = true

      // Der Zug sollte nicht gültig sein, weil das Startfeld besetzt ist
      ruleController.validateMove(piece1, gameState) shouldBe false
    }

    "not allow a piece to move onto an occupied field by the same player" in {
      piece1.isOnField = true
      gameState.dice.lastRoll = 4

      // Die Spielfigur von Player1 befindet sich auf dem Feld 0 und der Würfelwert ist 4
      // Es wird geprüft, ob das Ziel-Feld (feld[4]) besetzt ist und ein anderes Player-Piece vorhanden ist
      val landingIndex = (piece1.field.position + gameState.dice.lastRoll) % gameBoard.fields.length
      val landingField = gameBoard.fields(landingIndex)

      landingField.isOccupied = true
      landingField.piece = Some(piece1)  // Das Ziel-Feld ist von Player1 besetzt

      // Der Zug sollte hier nicht gültig sein, da das Ziel-Feld von Player1 selbst belegt ist
      ruleController.validateMove(piece1, gameState) shouldBe false
    }

    "allow moving a piece onto an occupied field by another player" in {
      piece1.isOnField = true
      gameState.dice.lastRoll = 4

      // Berechne das Ziel-Feld
      val landingIndex = (piece1.field.position + gameState.dice.lastRoll) % gameBoard.fields.length
      val landingField = gameBoard.fields(landingIndex)

      // Das Ziel-Feld ist besetzt von einem anderen Spieler
      landingField.isOccupied = true
      landingField.piece = Some(piece2)  // Andere Spielfigur auf dem Ziel-Feld

      // Der Zug sollte hier gültig sein, weil das Ziel-Feld von einem anderen Spieler besetzt ist
      ruleController.validateMove(piece1, gameState) shouldBe true
    }

    "allow moving a piece onto an unoccupied field when piece is on the field" in {
      piece1.isOnField = true
      gameState.dice.lastRoll = 5
      val landingIndex = (piece1.field.position + gameState.dice.lastRoll) % gameBoard.fields.length
      val landingField = gameBoard.fields(landingIndex)

      // Das Ziel-Feld ist nicht besetzt
      landingField.isOccupied shouldBe false

      // Validieren des Zugs
      ruleController.validateMove(piece1, gameState) shouldBe true
    }


  }
}