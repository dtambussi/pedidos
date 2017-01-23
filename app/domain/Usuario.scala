package domain

import javax.inject.Inject

import anorm.{RowParser, ~}
import anorm.SqlParser._
import play.api.db.Database

case class Usuario(
  id: Long,
  status: Status,
  nickname: String,
  roles: Seq[Rol] = Seq())

class UsuarioRepo @Inject()(db: Database, rolRepo: RolRepo) {

  val parser: RowParser[Usuario] = {
      long("id") ~
      int("status") ~
      str("nickname") map {
      case id ~ status ~ nickname =>
        Usuario(id, Status.valueOf(status), nickname, rolRepo.findByUsuario(id))
    }
  }

}