package model

import java.util.concurrent.atomic.AtomicInteger

import com.fasterxml.jackson.databind.JsonMappingException
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.json.{JsObject, Json}

import scalax.io._

class Room(
            val id: Long = Room.nextId(),
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

  def save() = {
    val gameState = this.toJson.toString()

    Logger.info(s"Saving room state='$gameState'")

    val uuid = s"${Room.storePath}$id.game"
    val gameFile: Seekable = Resource.fromFile(uuid)
    gameFile.truncate(0)
    gameFile.write(gameState)
  }

  def validatePassword(idParam: Long, passwordParam: String) = {
    id == idParam && password == passwordParam
  }

}

object Room {

  private val storePath = "saved/"

  def load(id: Long = 0, password: String): Option[Room] = {

    val uuid = s"${Room.storePath}$id.game"
    val roomString = Resource.fromFile(uuid).string(Codec.UTF8)

    try {
      val room = new Room(Json.parse(roomString).as[JsObject])
      if (room.validatePassword(id, password)) {
        Logger.info(s"Game loaded: id=$id, password=$password")
        Some(room)
      } else {
        Logger.info(s"Cannot load game: invalid credentials id=$id, password=$password")
        None
      }
    } catch {
      case e: JsonMappingException =>
        Logger.info(s"Cannot load game: id=$id, password=$password, exception=$e")
        None
    }

  }

  def fromJson(json: JsObject): Room = {

    val id = (json \ "id").as[Long]
    val password = (json \ "password").as[String]
    val player1 = (json \ "player1").as[String]
    val player2 = (json \ "player2").as[String]
    val game = new GameEngine((json \ "game").as[JsObject])
    val createdAt = (json \ "createdAt").as[DateTime]

    new Room(id, password, player1, player2, game, createdAt)

  }

  private val currentId = new AtomicInteger(0)

  def nextId() = {
    currentId.getAndIncrement()
  }


}
