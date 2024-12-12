package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CommandManagerSpec extends AnyFlatSpec with Matchers {

class incrCommand extends Command {
  var state:Int =0
  override def doStep(): Unit = state+=1

  override def undoStep(): Unit = state-=1

  override def redoStep(): Unit = state+=1

}

"A Command" should "have a do step" in {
    val command = new incrCommand
    command.state should be(0)
    command.doStep()
    command.state should be(1)
    command.doStep()
    command.state should be(2)
  }

  it should "have an undo step" in {
    val command = new incrCommand
    command.state should be(0)
    command.doStep()
    command.state should be(1)
    command.undoStep()
    command.state should be(0)
  }

  it should "have a redo step" in {
    val command = new incrCommand
    command.state should be(0)
    command.doStep()
    command.state should be(1)
    command.undoStep()
    command.state should be(0)
    command.redoStep()
    command.state should be(1)
  }
}