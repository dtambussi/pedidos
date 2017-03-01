package domain

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object Roles extends Enumeration {

  type Roles = Value
  val UsuarioRegistrado = Value("UsuarioRegistrado")
  val UsuarioNoRegistrado = Value("UsuarioNoRegistrado")

  implicit val myEnumFormat = new Format[Roles] {
    def reads(json: JsValue) = JsSuccess(Roles.withName(json.as[String]))
    def writes(myEnum: Roles) = JsString(myEnum.toString)
  }
}
