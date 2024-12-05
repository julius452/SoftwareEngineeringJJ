package controller

import memento.Caretaker
import model.Player
import view.{ConsoleView, InputHandler}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._

class PlayerControllerSpec extends AnyWordSpec with MockitoSugar {

  "PlayerController" should {

    "displayInitializePlayers and return a list of players" in {
      // Mocks
      val mockConsoleView = mock[ConsoleView]
      val mockInputHandler = mock[InputHandler]
      val mockCaretaker = mock[Caretaker]

      // Stubbing
      when(mockConsoleView.displayAskForPlayersCount()).thenReturn("How many players? (2-4)")
      when(mockConsoleView.displayWrongInput()).thenReturn("Invalid input. Try again.")
      when(mockInputHandler.readInt()).thenReturn(2) // Simulate valid input
      when(mockConsoleView.displayAskForPlayerName(any[Int])).thenReturn("Enter player name:")
      when(mockInputHandler.readLine()).thenReturn("Player1", "Player2")
      when(mockConsoleView.displayPlayers(any[List[Player]])).thenReturn("Displaying players...")
      when(mockConsoleView.displayHappyWithPlayers()).thenReturn("Are you happy with players? (j/n)")
      when(mockConsoleView.displayChangePlayer()).thenReturn("Which player to change?")
      when(mockConsoleView.displayAskForNewPlayerName(any[Int])).thenReturn("Enter new name:")

      val playerController = new PlayerController {
        // Replace dependencies with mocks
        override val consoleView: ConsoleView = mockConsoleView
        override val inputHandler: InputHandler = mockInputHandler
      }

      val players = playerController.displayInitializePlayers()

      // Assertions
      assert(players.size == 2)
      assert(players.head.getName == "Player1")
      assert(players(1).getName == "Player2")

      // Verify interactions
      verify(mockConsoleView, atLeastOnce()).displayAskForPlayersCount()
      verify(mockConsoleView, atLeastOnce()).displayAskForPlayerName(any[Int])
      verify(mockInputHandler, atLeastOnce()).readLine()
    }

    "handle invalid player count input in displayInitializePlayers" in {
      val mockConsoleView = mock[ConsoleView]
      val mockInputHandler = mock[InputHandler]

      when(mockConsoleView.displayAskForPlayersCount()).thenReturn("How many players? (2-4)")
      when(mockConsoleView.displayWrongInput()).thenReturn("Invalid input. Try again.")
      when(mockInputHandler.readInt()).thenReturn(1, 5, 3) // Simulate invalid inputs, then valid

      val playerController = new PlayerController {
        override val consoleView: ConsoleView = mockConsoleView
        override val inputHandler: InputHandler = mockInputHandler
      }

      val players = playerController.displayInitializePlayers()

      assert(players.size == 3) // Final valid input
      verify(mockConsoleView, times(3)).displayAskForPlayersCount()
      verify(mockConsoleView, times(2)).displayWrongInput()
    }

    "handle changes to player names in happyWithPlayers" in {
      val mockConsoleView = mock[ConsoleView]
      val mockInputHandler = mock[InputHandler]
      val mockCaretaker = mock[Caretaker]

      when(mockConsoleView.displayPlayers(any[List[Player]])).thenReturn("Displaying players...")
      when(mockConsoleView.displayHappyWithPlayers()).thenReturn("Are you happy with players? (j/n)")
      when(mockInputHandler.readLine()).thenReturn("n", "PlayerNew", "j") // Not happy -> change -> happy
      when(mockConsoleView.displayChangePlayer()).thenReturn("Which player to change?")
      when(mockConsoleView.displayAskForNewPlayerName(any[Int])).thenReturn("Enter new name:")
      when(mockInputHandler.readInt()).thenReturn(1) // Change first player

      val playerController = new PlayerController {
        override val consoleView: ConsoleView = mockConsoleView
        override val inputHandler: InputHandler = mockInputHandler
      }

      val players = List(Player(1, "Player1"), Player(2, "Player2"))
      playerController.happyWithPlayers(players, mockCaretaker)

      assert(players.head.getName == "PlayerNew")
      assert(players(1).getName == "Player2")

      verify(mockCaretaker, atLeastOnce()).undo(any[Player])
      verify(mockCaretaker, atLeastOnce()).save(any[Player])
    }
  }
}
