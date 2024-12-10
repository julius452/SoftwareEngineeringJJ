package command

import scala.util.{Try, Success, Failure}
import view.ConsoleView

class CommandManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil
  private val consoleView = new ConsoleView()

  def doStep(command: Command): Try[Unit] = {
    Try {
      command.doStep()
    } match {
      case Success(_) =>
        undoStack = command :: undoStack
        redoStack = Nil // Redo-Stack leeren
        Success(())
      case Failure(exception) =>
        println(consoleView.displayError())
        Failure(exception)
    }
  }

  def undoStep(): Try[Unit] = {
    undoStack match {
      case Nil =>
        println(consoleView.displayErrorUndo())
        Success(())
      case head :: stack =>
        Try {
          head.undoStep()
        } match {
          case Success(_) =>
            undoStack = stack
            redoStack = head :: redoStack
            Success(())
          case Failure(exception) =>
            println(consoleView.displayError())
            Failure(exception)
        }
    }
  }
  

  def redoStep(): Try[Unit] = {
    redoStack match {
      case Nil =>
        println(consoleView.displayErrorRedo())
        Success(())
      case head :: stack =>
        Try {
          head.redoStep()
        } match {
          case Success(_) =>
            redoStack = stack
            undoStack = head :: undoStack
            Success(())
          case Failure(exception) =>
            println(consoleView.displayError())
            Failure(exception)
        }
    }
  }
}
