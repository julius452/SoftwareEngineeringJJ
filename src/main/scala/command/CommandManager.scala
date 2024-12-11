package command

import scala.util.{Try, Success, Failure}
import view.ConsoleView

class CommandManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Try[Unit] = {
    Try {
      command.doStep()
    } match {
      case Success(_) =>
        undoStack = command :: undoStack
        redoStack = Nil // Redo-Stack leeren
        Success(())
      case Failure(exception) =>
        println(ConsoleView.displayError())
        Failure(exception)
    }
  }

  def undoStep(): Try[Unit] = {
    undoStack match {
      case Nil =>
        println(ConsoleView.displayErrorUndo())
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
            println(ConsoleView.displayError())
            Failure(exception)
        }
    }
  }

  def redoStep(): Try[Unit] = {
    redoStack match {
      case Nil =>
        println(ConsoleView.displayErrorRedo())
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
            println(ConsoleView.displayError())
            Failure(exception)
        }
    }
  }
}
