package domain

sealed trait Roles { val name: String; val value: Int }

object Roles {
  case object UsuarioRegistrado extends Roles { val name = "UsuarioRegistrado"; val value = 1 };
  case object UsuarioNoRegistrado extends Roles { val name = "UsuarioNoRegistrado"; val value = 2 };

  def valueOf(value: Int): Roles = value match {
    case UsuarioRegistrado.value => UsuarioRegistrado
    case UsuarioNoRegistrado.value => UsuarioNoRegistrado
  }
}
