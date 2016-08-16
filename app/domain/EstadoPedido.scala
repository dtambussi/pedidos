package domain

sealed trait EstadoPedido { val name: String; val value: Int }

object EstadoPedido {
  case object Generado extends EstadoPedido { val name = "Generado"; val value = 1 }
  case object Pendiente extends EstadoPedido { val name = "Pendiente"; val value = 2 }
  case object Confeccionado extends EstadoPedido { val name = "Confeccionado"; val value = 3 }
  case object Entregado extends EstadoPedido { val name = "Entregado"; val value = 4 }
  case object Cancelado extends EstadoPedido { val name = "Cancelado"; val value = 5 }

  def valueOf(value: Int): EstadoPedido = value match {
    case Generado.value => Generado
    case Pendiente.value => Pendiente
    case Confeccionado.value => Confeccionado
    case Entregado.value => Entregado
    case Cancelado.value => Cancelado
  }
}
