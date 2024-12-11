package controller

import util.Observable

trait ControllerInterface extends Observable{
  def getCurrentStateAsString: String
  def eval(input: String): Unit
}
