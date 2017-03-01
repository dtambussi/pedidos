package domain

import javax.inject.Inject

import anorm.{RowParser, ~}
import anorm.SqlParser._
import domain.Status.Status
import play.api.db.Database

case class Usuario(
  id: Long,
  status: Status,
  nickname: String,
  roles: Seq[Rol] = Seq())

class UsuarioRepo @Inject()(db: Database, rolRepo: RolRepo) {

  val parser: RowParser[Usuario] = {
      long("id") ~
      str("status") ~
      str("nickname") map {
      case id ~ status ~ nickname =>
        Usuario(id, Status.withName(status), nickname, rolRepo.findByUsuario(id))
    }
  }

}