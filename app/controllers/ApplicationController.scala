package controllers

import domain.{EstadoItemDePedido, EstadoPedido, EstadoSugerencia}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

class ApplicationController extends Controller {

  def estadosDePedido() : Action[AnyContent] = Action {
    Ok(Json.arr(EstadoPedido.values))
  }

  def estadosDeItemDePedido() : Action[AnyContent] = Action {
    Ok(Json.arr(EstadoItemDePedido.values))
  }

  def estadosDeSugerencia() : Action[AnyContent] = Action {
    Ok(Json.arr(EstadoSugerencia.values))
  }
}
