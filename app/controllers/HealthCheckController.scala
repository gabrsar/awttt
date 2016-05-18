package controllers

import play.api.mvc.{Action, Controller}


class HealthCheckController extends Controller{

  def healthCheck() = Action {
    Ok("I'm alive!")
  }

}
