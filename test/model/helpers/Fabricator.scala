package model.helpers

import model.GameEngine

import scala.util.Random

object Fabricator {

  val DEFAULT_STRING_SIZE = 10

  def game() = {
    new GameEngine()
  }

  def string(size:Int=DEFAULT_STRING_SIZE) = Random.alphanumeric.take(size).mkString

  def playerName() = string() + Random.nextInt()


}
