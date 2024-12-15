package controller

import model.{Field, Player}
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
  def getValidMoves: List[(Int,String)]
  def getIsExecutePlayerMove: Boolean
  def getCurrentPlayerNumber: Int
  def getFieldByPosition(position: Int): Field
  def getPlayers(): List[Player]
  def getStartHouseByPlayerAndIndex(playerNumber: Int, index: Int): Field
  def getLastPlayer: Player
}
