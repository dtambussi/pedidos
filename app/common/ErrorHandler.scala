package common

import org.slf4j.MDC
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc.{Result, RequestHeader}

import scala.concurrent.Future
import scala.util.Try

class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, m: String): Future[Result] = {
    val message = if(m.isEmpty) "there was a problem in the request, please check the path and the headers" else m
    Logger.warn(s"There was a client error with statusCode $statusCode in ${request.method} ${request.path} with message: $message")
    Future.successful(Status(statusCode)(Json.obj("error" -> true, "message" -> message)))
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    val requestId = Try(MDC.get("requestId")).getOrElse{Logger.warn("couldnt get the requestId from MDC");""}
    val message = s"There was a server error in ${request.method} ${request.path} with requestId $requestId"
    Logger.error(message, exception)
    Future.successful(InternalServerError(Json.obj("error" -> true, "message" -> exception.getMessage)))
  }

}