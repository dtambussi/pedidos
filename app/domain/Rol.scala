package domain

import javax.inject.Inject

import anorm._
import anorm.SqlParser._
import domain.Status.Status
import org.joda.time.DateTime
import play.api.db.Database

case class Rol(
  id: Long,
  status: Status,
  nombre: String,
  descripcion: String,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime)

class RolRepo @Inject()(db: Database) {

  val tableName = "Rol"

  val parser: RowParser[Rol] = {
      long("id") ~
      int("status") ~
      str("nombre") ~
      str("descripcion") ~
      get[DateTime]("fecha_creacion") ~
      get[DateTime]("fecha_ultima_modificacion") map {
      case id ~ status ~ nombre ~ descripcion ~ fechaCreacion ~ fechaUltimaModificacion =>
        Rol(id, Status(status), nombre, descripcion, fechaCreacion, fechaUltimaModificacion)
    }
  }

  def findById(id: Long): Option[Rol] = {
    val selectQuery = s"SELECT * FROM $tableName WHERE id = {id}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('id -> id).as(parser.singleOpt)
    }
  }

  def findByUsuario(idUsuario: Long): List[Rol] = {
    val selectQuery = s"SELECT i.* FROM $tableName r " +
      s"JOIN Usuario_Rol ur ON ur.id_rol = r.id " +
      s"WHERE id_usuario = {idUsuario}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('idUsuario -> idUsuario).as(parser.*)
    }
  }
}