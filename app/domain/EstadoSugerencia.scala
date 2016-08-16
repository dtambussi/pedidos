package domain

sealed trait EstadoSugerencia { val name: String; val value: Int }

object EstadoSugerencia {
  case object NoPublicado extends EstadoSugerencia { val name = "NoPublicado"; val value = 1 }
  case object Publicado extends EstadoSugerencia { val name = "Publicado"; val value = 2 }

  def valueOf(value: Int): EstadoSugerencia = value match {
    case NoPublicado.value => NoPublicado
    case Publicado.value => Publicado
  }
}
