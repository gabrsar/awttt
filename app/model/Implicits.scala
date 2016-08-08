package model

import model.room.game.GameType
import play.api.libs.json.Reads

object Implicits {
  implicit val gameTypeFormatter = Reads.enumNameReads(GameType)
}