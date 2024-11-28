package memento

class PlayerMemento(private val state: String) {
  def getName: String = state
}