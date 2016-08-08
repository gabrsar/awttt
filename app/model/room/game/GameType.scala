package model.room.game

import play.api.libs.json.{Format, Json, Reads}


object GameType extends Enumeration {
  type GameType = Value
  val HUMAN_VS_HUMAN, HUMAN_VS_CPU, CPU_VS_CPU = Value


}


