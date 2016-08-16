package domain

sealed trait Status { val name: String; val value: Int }

object Status {
  case object Inactive extends Status { val name = "Inactive"; val value = 1 }
  case object Active extends Status { val name = "Active"; val value = 2 }
  case object Deleted extends Status { val name = "Deleted"; val value = 3 }

  def valueOf(value: Int): Status = value match {
    case Inactive.value => Inactive
    case Active.value => Active
    case Deleted.value => Deleted
  }
}
