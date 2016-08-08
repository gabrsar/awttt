package services

import javax.inject.Inject

import model.room.Room
import model.room.game.GameEngine
import model.room.game.GameType.GameType

import scala.util.Random.{nextLong, nextString}

class RoomsService @Inject()(filesService: FilesService) {

  val playerPasswordSize = 5

  def makeRoomFilePath(id: Long) = s"rooms/$id.txt"

  def create(gameType: GameType, password: String): Room = {

    val room = new Room(
      nextLong(),
      password,
      nextString(playerPasswordSize),
      nextString(playerPasswordSize),
      new GameEngine()
    )

    filesService.save(makeRoomFilePath(room.id), room.toJson.toString())

    room
  }

}
