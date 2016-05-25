package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import services.FilesService

class HelpController @Inject()(filesService: FilesService) extends Controller {

  def index = Action {
    val helpFile = "public/messages/help.txt"
    Ok(filesService.loadAsString(helpFile))
  }

}
