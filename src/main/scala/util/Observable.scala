package util

import model.GameState

trait Observer {
  def update(gameState: GameState): Unit
}

class Observable {
  private var observers: List[Observer] = List()

  def addObserver(observer: Observer): Unit = {
    observers = observer :: observers
  }

  def removeObserver(observer: Observer): Unit = {
    observers = observers.filterNot(_ == observer)
  }

  def notifyObservers(gameState: GameState): Unit = {
    observers.foreach(_.update(gameState))
  }
}

