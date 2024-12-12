package view

import controller.ControllerInterface
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TUISpec extends AnyFlatSpec with Matchers {

  class TestController extends ControllerInterface {
    var undoCalled = false
    var redoCalled = false
    var evalCalledWith: Option[String] = None
    var currentState: String = "initial state"

    override def undo(): Unit = undoCalled = true
    override def redo(): Unit = redoCalled = true
    override def eval(input: String): Unit = evalCalledWith = Some(input)
    override def getCurrentStateAsString: String = currentState
    override def add(observer: util.Observer): Unit = {}
    override def notifyObservers(): Unit = {}

    override def doStep(input: Int): Unit = {}
  }

  "A TUI" should "call controller's undo method when input is 'u'" in {
    val controller = new TestController
    val tui = new TUI(controller)

    tui.processInput("u")
    controller.undoCalled should be(true)
  }

  it should "call controller's redo method when input is 'r'" in {
    val controller = new TestController
    val tui = new TUI(controller)

    tui.processInput("r")
    controller.redoCalled should be(true)
  }

  it should "call controller's eval method with the correct input" in {
    val controller = new TestController
    val tui = new TUI(controller)
    val input = "some input"

    tui.processInput(input)
    controller.evalCalledWith should be(Some(input))
  }

  it should "print the current state when update is called" in {
    val controller = new TestController
    controller.currentState = "current state"
    val tui = new TUI(controller)

    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      tui.update()
    }
    outputStream.toString.replace("\r\n", "\n") should be("current state\n")
  }
}