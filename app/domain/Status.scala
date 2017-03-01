package domain

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object Status extends Enumeration {

  type Status = Value
  val Inactive = Value(1, "Inactive")
  val Active = Value(2, "Active")
  val Deleted = Value(3, "Deleted")

  implicit val myEnumFormat = new Format[Status] {
    def reads(json: JsValue) = JsSuccess(Status.withName(json.as[String]))
    def writes(myEnum: Status) = JsString(myEnum.toString)
  }
}