package memento

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import model.{Player}

class CaretakerSpec extends AnyFlatSpec with Matchers {

  "save should store the player's state" should "push a new memento onto the stack" in {
    val player = new Player(1, "Alice")
    val caretaker = new Caretaker
    caretaker.save(player)
    caretaker.getMementos(player.getPlayerNumber()).top should be(player.save())
  }

  "undo should restore the player's previous state" should "pop the memento from the stack and restore it" in {
    val player = new Player(1, "Alice")
    val caretaker = new Caretaker
    caretaker.save(player)
    caretaker.undo(player)
    caretaker.getMementos(player.getPlayerNumber()) should be(empty)
  }

  "undo should handle empty stack gracefully" should "not throw an exception and print a message" in {
    val player = new Player(1, "Alice")
    val caretaker = new Caretaker
    noException should be thrownBy caretaker.undo(player)
  }

  "save should create a new stack for a new player" should "initialize a new stack for the player" in {
    val player1 = new Player(1, "Alice")
    val player2 = new Player(2, "Bob")
    val caretaker = new Caretaker
    caretaker.save(player1)
    caretaker.save(player2)
    caretaker.getMementos(player1.getPlayerNumber()).top should be(player1.save())
    caretaker.getMementos(player2.getPlayerNumber()).top should be(player2.save())
  }
}