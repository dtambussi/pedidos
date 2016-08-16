package domain

sealed trait EstadoItemDePedido { val name: String; val value: Int }

object EstadoItemDePedido {
  case object Generado extends EstadoItemDePedido { val name = "Generado"; val value = 1 }
  case object Pendiente extends EstadoItemDePedido { val name = "Pendiente"; val value = 2 }
  case object Confeccionado extends EstadoItemDePedido { val name = "Confeccionado"; val value = 3 }
  case object Entregado extends EstadoItemDePedido { val name = "Entregado"; val value = 4 }
  case object Cancelado extends EstadoItemDePedido { val name = "Cancelado"; val value = 5 }

  def valueOf(value: Int): EstadoItemDePedido = value match {
    case Generado.value => Generado
    case Pendiente.value => Pendiente
    case Confeccionado.value => Confeccionado
    case Entregado.value => Entregado
    case Cancelado.value => Cancelado
  }
}
