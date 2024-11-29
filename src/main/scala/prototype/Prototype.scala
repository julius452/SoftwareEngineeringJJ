package prototype

trait Prototype[T] {
  def makeClone(): T
}
