package domain

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object EstadoPedido extends Enumeration {

  type EstadoPedido = Value
  val Generado = Value(1, "Generado")
  val Pendiente = Value(2, "Pendiente")
  val Confeccionado = Value(3, "Confeccionado")
  val Entregado = Value(4, "Entregado")
  val Cancelado = Value(5, "Cancelado")

  implicit val myEnumFormat = new Format[EstadoPedido] {
    def reads(json: JsValue) = JsSuccess(EstadoPedido.withName(json.as[String]))
    def writes(myEnum: EstadoPedido) = JsString(myEnum.toString)
  }
}
