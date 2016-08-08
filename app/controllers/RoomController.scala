package controllers

import javax.inject.Inject

import model.Implicits._
import model.room.game.GameType.GameType
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.BodyParsers.parse
import play.api.mvc._
import services.RoomsService

import scala.util._


class RoomController @Inject()(roomService: RoomsService) {

  case class CreateRoomRequest(gameType: GameType, password: String)

  def createRoom = Action(parse.json) { request =>
    jsonToCreateGameRequest(request.body) match {
      case Some(rr) =>
        Results.Ok(roomService.create(rr.gameType, rr.password).toJson)
      case _ =>
        Results.BadRequest
    }
  }

  def jsonToCreateGameRequest(json: JsValue): Option[CreateRoomRequest] = {
    val possibleCreateGameRequest: Try[CreateRoomRequest] = for {
      gameType <- Try((json \ "gameType").as[GameType])
      password <- Try((json \ "password").as[String])
    } yield CreateRoomRequest(gameType, password)

    possibleCreateGameRequest match {
      case Success(a: CreateRoomRequest) => Some(a)
      case Failure(e) =>
        Logger.error(s"Could not parse json='$json' to CreateGameRequest. reason=$e")
        None
    }
  }

}
