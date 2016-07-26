package controllers

import play.api.mvc.{Action, Controller, AnyContent}

class PingController extends Controller {

  def ping(): Action[AnyContent] = Action {
    Ok("pong")
  }

}