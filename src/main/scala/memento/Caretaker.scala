package memento

import model.Player

import scala.collection.mutable

class Caretaker {
  private val mementos: mutable.Map[Int, mutable.Stack[PlayerMemento]] = mutable.Map()

  // Speichert den Zustand eines bestimmten Spielers
  def save(player: Player): Unit = {
    val stack = mementos.getOrElseUpdate(player.getPlayerNumber(), mutable.Stack[PlayerMemento]())
    stack.push(player.save())
  }

  // Stellt den vorherigen Zustand eines bestimmten Spielers wieder her
  def undo(player: Player): Unit = {
    val stack = mementos.getOrElse(player.getPlayerNumber(), mutable.Stack[PlayerMemento]())
    if (stack.nonEmpty) {
      player.restore(stack.pop())
    } else {
      println(s"Kein vorheriger Name für Spieler ${player.getPlayerNumber()} vorhanden!")
    }
  }

  def getMementos: mutable.Map[Int, mutable.Stack[PlayerMemento]] = mementos

}
