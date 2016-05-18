package controllers

import javax.inject.Inject

import play.api.mvc.Controller
import play.api.mvc.Action
import services.FilesService

class IndexController @Inject()(filesService:FilesService) extends Controller{

  def index = Action { implicit request =>
    val messageFile = "public/messages/hello.txt"
    Ok(filesService.loadAsString(messageFile))
  }

}
