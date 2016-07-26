package actions

import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Result, Controller}

trait BaseController extends Controller {

  def badRequest(msg: String): Result = {
    Logger.debug(s"BadRequest: error '$msg'")
    BadRequest(Json.obj("error" -> true, "message" -> msg))
  }

  def error(msg: String): Result = {
    Logger.debug(s"Error: '$msg'")
    InternalServerError(Json.obj("error" -> true, "message" -> msg))
  }

  def ok(json: JsValue): Result = {
    Ok(Json.obj("error" -> false, "message" -> "", "content" -> json))
  }

  def ok(message: String): Result = {
    Ok(Json.obj("error" -> false, "message" -> message))
  }

  def redirect(redirectLink: String): Result = {
    Ok(Json.obj("error" -> false, "message" -> "", "type" -> "redirect", "redirect" -> redirectLink))
  }

}
