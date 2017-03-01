package domain

import javax.inject.Inject

import anorm._
import anorm.SqlParser._
import domain.Status.Status
import org.joda.time.DateTime
import play.api.db.Database
import play.api.libs.json._
import ItemDeMenuRepo.itemDeMenuFormat

case class Menu(
  id: Long,
  status: Status,
  nombre: String,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime,
  vigente: Boolean,
  items: Seq[ItemDeMenu] = Seq())

object MenuRepo {
  implicit val menuFormat = Json.format[Menu]
}

class MenuRepo @Inject()(db: Database, itemDeMenuRepo: ItemDeMenuRepo) {

  val tableName = "Menu"

  val parser: RowParser[Menu] = {
      long("id") ~
      int("status") ~
      str("nombre") ~
      get[DateTime]("fecha_creacion") ~
      get[DateTime]("fecha_ultima_modificacion") ~
      bool("vigente") map {
      case id ~ status ~ nombre ~ fechaCreacion ~ fechaUltimaModificacion ~ vigente =>
        Menu(id, Status(status), nombre, fechaCreacion, fechaUltimaModificacion, vigente, itemDeMenuRepo.findByMenu(id))
    }
  }

  def findById(id: Long): Option[Menu] = {
    val selectQuery = s"SELECT * FROM $tableName WHERE id = {id}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('id -> id).as(parser.singleOpt)
    }
  }

  def findLatestActiveMenu(): Option[Menu] = {
    val selectQuery = s"SELECT * FROM $tableName WHERE status = {status} ORDER BY id DESC limit 1"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('status -> Status.Active.id).as(parser.singleOpt)
    }
  }

}