package model.room

import model.room.game.GameEngine
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.json.{JsObject, Json}

class Room(
            val id: Long,
            val password: String = "",
            val player1: String,
            var player2: String = "",
            val game: GameEngine = new GameEngine(),
            val createdAt: DateTime = new DateTime()
            ) {

  Logger.info(s"Creating new Room #$id")

  def validatePlayerTurn(playerKey: String): Boolean = {

    val turnKey = if (game.currentPlayer == GameEngine.player1) player1 else player2

    if (playerKey == turnKey) {
      true
    } else {
      false
    }
  }

  def this(json: JsObject) {
    this(
      (json \ "id").as[Long],
      (json \ "password").as[String],
      (json \ "player1").as[String],
      (json \ "player2").as[String],
      new GameEngine((json \ "game").as[JsObject])
    )
  }

  def toJson: JsObject = {
    Json.obj(
      "password" -> password,
      "createdAt" -> createdAt,
      "player1" -> player1,
      "player2" -> player2,
      "id" -> id,
      "game" -> game.state
    )
  }

  def validatePassword(idParam: Long, passwordParam: String) = {
    id == idParam && password == passwordParam
  }

}

object Room {

  def fromJson(json: JsObject): Room = {

    val id = (json \ "id").as[Long]
    val password = (json \ "password").as[String]
    val player1 = (json \ "player1").as[String]
    val player2 = (json \ "player2").as[String]
    val game = new GameEngine((json \ "game").as[JsObject])
    val createdAt = (json \ "createdAt").as[DateTime]

    new Room(id, password, player1, player2, game, createdAt)

  }

}
