package domain

sealed trait Categoria { val name: String; val value: Int }

object Categoria {
  case object Entradas extends Categoria { val name = "Entradas"; val value = 1 }
  case object PlatosPrincipales extends Categoria { val name = "PlatosPrincipales"; val value = 2 }
  case object Postres extends Categoria { val name = "Postres"; val value = 3 }
  case object Bebidas extends Categoria { val name = "Bebidas"; val value = 4 }
  case object Cafeteria extends Categoria { val name = "Cafeteria"; val value = 5 }

  def valueOf(value: Int): Categoria = value match {
    case Entradas.value => Entradas
    case PlatosPrincipales.value => PlatosPrincipales
    case Postres.value => Postres
    case Bebidas.value => Bebidas
    case Cafeteria.value => Cafeteria
  }
}
