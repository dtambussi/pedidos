package controllers

import domain.{Categoria, EstadoItemDePedido, EstadoPedido, EstadoSugerencia}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

class ApplicationController extends Controller {

  def categorias() : Action[AnyContent] = Action {
    Ok(Json.arr(Categoria.values))
  }

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
