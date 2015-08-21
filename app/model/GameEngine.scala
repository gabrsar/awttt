package model

import play.api.libs.json.{JsObject, Json}

class GameEngine(
                  val board: Array[Array[String]] = Array.tabulate(3, 3)((x, y) => GameEngine.empty),
                  var currentPlayer: String = GameEngine.player1,
                  var round: Int = 1) {

  def this(json: JsObject) {
    this(
      (json \ "board").as[Array[Array[String]]],
      (json \ "currentPlayer").as[String],
      (json \ "round").as[Int]
    )
  }

  def state = {
    Json.obj(
      "board" -> board,
      "currentPlayer" -> currentPlayer,
      "status" -> checkForVictory,
      "round" -> round
    )
  }

  def executeMovement(row: Integer, col: Integer): Boolean = {
    println(row, col)
    if (isEmpty(row, col)) {
      board(row)(col) = currentPlayer
      nextRound()
      true
    } else {
      false
    }
  }

  private def nextRound() = {

    round = round + 1

    if (currentPlayer == GameEngine.player1) {
      currentPlayer = GameEngine.player2
    } else {
      currentPlayer = GameEngine.player1
    }

  }

  def draw(): String = {
    Json.toJson(board).toString()
  }

  def isEmpty(row: Integer, col: Integer): Boolean = {
    if (GameEngine.isValidMovement(row, col)) {
      board(row)(col) == GameEngine.empty
    } else {
      false
    }
  }

  def checkForVictory = {

    def checkForPlayer(p: String) = {

      def hasRowWinner(r: Integer) = board(r)(0) == p && board(r)(1) == p && board(r)(2) == p
      def hasColWinner(c: Integer) = board(0)(c) == p && board(1)(c) == p && board(2)(c) == p
      def hasPriDiagonalWinner = board(0)(0) == p && board(1)(1) == p && board(2)(2) == p
      def hasSecDiagonalWinner = board(0)(2) == p && board(1)(1) == p && board(2)(0) == p

      lazy val rowWinner = hasRowWinner(0) || hasRowWinner(1) || hasRowWinner(2)
      lazy val colWinner = hasColWinner(0) || hasColWinner(1) || hasColWinner(2)
      lazy val diagonalWinner = hasPriDiagonalWinner || hasSecDiagonalWinner

      if (round < GameEngine.minRoundsToSomeoneWin) {
        GameEngine.continue
      } else if (round < GameEngine.maxRounds) {
        if (rowWinner || colWinner || diagonalWinner) {
          GameEngine.winner
        } else {
          GameEngine.continue
        }
      } else {
        GameEngine.draw
      }
    }

    //TODO: Find a pretty solution to this.
    val player1 = checkForPlayer(GameEngine.player1)
    val player2 = checkForPlayer(GameEngine.player2)

    if (player1 == GameEngine.winner) {
      GameEngine.player1
    } else if (player2 == GameEngine.winner) {
      GameEngine.player2
    } else {
      player1 // Nobody wins, but player1 contains flag continue or draw
    }
  }


}

object GameEngine {

  val empty = " "
  val player1 = "X"
  val player2 = "O"
  val draw = "="
  val winner = "!"
  val maxRounds = 9
  val minRoundsToSomeoneWin = 5
  val continue = "#"

  def isValidMovement(row: Integer, col: Integer) =
    (row >= 0 && row < 3) && (col >= 0 && col < 3)

  def fromJson(json: JsObject): GameEngine = {
    new GameEngine(
      (json \ "board").as[Array[Array[String]]],
      (json \ "currentPlayer").as[String],
      (json \ "round").as[Int]
    )
  }

  object MovimentStatus {
    val DONE = 0
    val NOT_EMPTY = 1
    val INVALID = 2
  }


}
