package domain

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object EstadoSugerencia extends Enumeration {

  type EstadoSugerencia = Value
  val NoPublicado = Value(1, "NoPublicado")
  val Publicado = Value(2, "Publicado")

  implicit val myEnumFormat = new Format[EstadoSugerencia] {
    def reads(json: JsValue) = JsSuccess(EstadoSugerencia.withName(json.as[String]))
    def writes(myEnum: EstadoSugerencia) = JsString(myEnum.toString)
  }
}