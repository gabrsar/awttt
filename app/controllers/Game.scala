package controllers

import model.{GameEngine, Room}
import play.api.Logger
import play.api.libs.json.{Json, JsObject}
import play.api.mvc.{Result, Action, Controller}

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

    Logger.info(s"New user requesting to Join on Room #$id with password: $password")
    val room = Room.load(id, password)

    room match {
      case Some(trulyRoom) => {
        val player2: String = Random.alphanumeric.take(10).mkString
        trulyRoom.player2 = player2
        Ok("xxxx")
      }
      case None => BadGateway("Vish")
    }
  }

  def draw(id: Long, password: String) = Action { implicit request =>
    Logger.info(s"Drawing game #$id")

    onValidRoom(id, password) { room =>
      Ok(room.game.state)
    }
  }

  private def onValidRoom(id: Long, password: String)(f: (Room) => Result): Result = {
    Room.load(id, password) match {
      case Some(room) => f(room)
      case None => NotFound(":(")
    }
  }

  def play(id: Long, password: String, playerKey: String, row: Int, col: Int) = Action { implicit request =>
    Logger.info(s"Executing movement for playerKey:$playerKey")


      onValidRoom(id, password) { room =>
        if (room.game.executeMovement(row, col)) {
          Ok("")
        }



        Ok("")
      }
      Ok("")

  }
}