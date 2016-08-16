package domain

case class Usuario(id: Long, status: Status, nickname: String, roles: Seq[Rol] = Nil)

case class UsuarioRol(idUsuario: Long, idRol: Long)
