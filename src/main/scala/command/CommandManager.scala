package command

class CommandManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    redoStack = Nil // Redo-Stack leeren
    command.doStep()
  }

  def undoStep(): Unit = {
    undoStack match {
      case Nil => println("Kein Zug zum Rückgängig machen.")
      case head :: stack =>
        head.undoStep()
        undoStack = stack
        redoStack = head :: redoStack
    }
  }

  def redoStep(): Unit = {
    redoStack match {
      case Nil => println("Kein Zug zum Wiederholen.")
      case head :: stack =>
        head.redoStep()
        redoStack = stack
        undoStack = head :: undoStack
    }
  }
}
