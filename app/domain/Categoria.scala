package domain

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object Categoria extends Enumeration {

  type Categoria = Value
  val Entradas = Value(1, "Entradas")
  val PlatosPrincipales = Value(2, "PlatosPrincipales")
  val Postres = Value(3, "Postres")
  val Bebidas = Value(4, "Bebidas")
  val Cafeteria = Value(5, "Cafeteria")

  implicit val myEnumFormat = new Format[Categoria] {
    def reads(json: JsValue) = JsSuccess(Categoria.withName(json.as[String]))
    def writes(myEnum: Categoria) = JsString(myEnum.toString)
  }
}