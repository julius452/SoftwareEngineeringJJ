package controller

import util.Observable

trait ControllerInterface extends Observable{
  def getCurrentStateAsString: String
  def eval(input: String): Unit
  def undo(): Unit
  def redo(): Unit
  def doStep(input: Int): Unit
  def controllerStateAsString: String
  def getCurrentPlayerSetUpNumber: Int
  def getCurrentPlayerName: String
  def getLastRoll: Int
  def getRollCounter: Int
  def getPlayerCount: Int
}
