package memento

class PlayerMemento(private val state: String) extends Memento {
  def getName(): String = state

  override def equals(obj: Any): Boolean = obj match {
    case that: PlayerMemento => this.state == that.state
    case _ => false
  }

  override def hashCode(): Int = state.hashCode
}