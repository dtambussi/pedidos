package domain

import javax.inject.Inject

import anorm.JodaParameterMetaData._
import anorm.SqlParser._
import anorm._
import common.formatters.DateTimeFormatter.dateTimeFormatter
import domain.ItemDeMenuRepo.itemDeMenuFormat
import domain.Status.Status
import org.joda.time.DateTime
import play.api.db.Database
import play.api.libs.json._

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
  val itemsTableName = "Menu_ItemDeMenu"

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

  def findLatestActiveMenu(): Option[Menu] = {
    val selectQuery = s"SELECT * FROM $tableName WHERE status = {status} ORDER BY id DESC limit 1"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('status -> Status.Active.id).as(parser.singleOpt)
    }
  }

  def findById(id: Long): Option[Menu] = {
    val selectQuery = s"SELECT * FROM $tableName WHERE id = {id}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('id -> id).as(parser.singleOpt)
    }
  }

  def create(menu: Menu): Either[String, Menu] = {
    db.withConnection { implicit connection =>
      val menuId: Option[Long] = SQL(s"insert into $tableName (status, nombre, fecha_creacion, fecha_ultima_modificacion, vigente) " +
        s"values ({status}, {nombre}, {fechaCreacion}, {fechaUltimaModificacion}, {vigente})"
      ).on('status -> menu.status.id, 'nombre -> menu.nombre, 'fechaCreacion -> menu.fechaCreacion,
        'fechaUltimaModificacion -> menu.fechaUltimaModificacion, 'vigente -> menu.vigente)
        .executeInsert()
      menuId.map { id =>
        menu.items.foreach { menuItem =>
          SQL(s"insert into $itemsTableName (id_menu, id_item_de_menu) values ({idMenu}, {idItemDeMenu})"
          ).on('idMenu -> id, 'idItemDeMenu -> menuItem.id)
            .executeInsert()
        }
      }
      menuId.flatMap(findById).toRight("menu.create.error")
    }
  }
}