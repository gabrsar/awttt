package controllers

import play.api.mvc.{Action, Controller}

object RoomsController extends Controller {

  def create() = Action(parse.json) { request =>

    val json = request.body

    (json \ "name", json \ "gameType", json \ "password") match {
      case name => Ok(name.toString())
      case _ => BadRequest("no name found")
    }


  }

}