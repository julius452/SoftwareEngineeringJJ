package memento

class PlayerMemento(private val state: String) extends Memento {
  def getName(): String = state
}