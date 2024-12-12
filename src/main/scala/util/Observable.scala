package util

trait Observer {
  def update(): Unit
  def isUpdated: Boolean
}

class Observable {
  var observers: Vector[Observer] = Vector()

  def add(observer: Observer): Unit = {
    observers = observer +: observers
  }

  def remove(observer: Observer): Unit = {
    observers = observers.filterNot(o => o == observer)
  }

  def notifyObservers(): Unit = {
    observers.foreach(o => o.update())
  }
}

