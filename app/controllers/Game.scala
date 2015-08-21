package controllers

import model.Room
import play.api.Logger
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller, Result}

import scala.util.Random

object Game extends Controller {

  def index = Action { implicit request =>
    val welcome =
      s"""Welcome to the AwTTT: Awesome Tic Tac Toe
         |To start a new game use:
         |GET /newgame/password - Result: {"id":xxx,"player1":"yyy"}
         |That's all Folks!
     """.stripMargin
    Ok(welcome);
  }

  def start(password: String) = Action { implicit request =>

    val player1: String = Random.alphanumeric.take(10).mkString
    val room: Room = new Room(password = password, player1 = player1)

    room.save()

    val result: JsObject = Json.obj("id" -> room.id, "player1" -> player1)
    Ok(result)
  }

  def join(id: Long, password: String) = Action { implicit request =>

    Logger.info(s"New user requesting to Join on Room id=$id, password=$password")

    onValidRoom(id, password) { room =>
      val player2: String = Random.alphanumeric.take(10).mkString
      room.player2 = player2
      room.save()
      Logger.info(s"Player 2 successfully joined to game")
      Ok(player2)
    }
  }

  def draw(id: Long, password: String) = Action { implicit request =>
    Logger.info(s"drawing game request")
    onValidRoom(id, password) { room =>
      Ok(room.game.state)
    }
  }

  def move(id: Long, password: String, playerKey: String, x: Int, y: Int) = Action { implicit request =>
    Logger.info(s"player request move: id=$id, password=$password, playerKey=$playerKey, x=$x, y=$y")

    onValidRoom(id, password) { room =>
      if (room.validatePlayerTurn(playerKey)) {
        if (room.game.executeMovement(x, y)) {
          room.save()
          Logger.info("movement executed with success")
          Ok(Json.obj("status" -> "success", "game" -> room.game.state))
        } else {
          Logger.info("invalid movement")
          Ok(Json.obj("status" -> "error", "reason" -> "invalid movement"))
        }
      } else {
        Logger.info("Not your turn")

        Forbidden("Not your turn")
      }
    }
  }

  private def onValidRoom(id: Long, password: String)(f: (Room) => Result): Result = {

    Room.load(id, password) match {
      case Some(room) =>
        Logger.info(s"Room load with success: id=$id, password=$password")
        f(room)
      case None =>
        Logger.info(s"Cannot load room: id=$id, password=$password")
        Forbidden(":(")
    }
  }
}