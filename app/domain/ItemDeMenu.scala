package domain

import javax.inject.Inject

import anorm.JodaParameterMetaData._
import anorm.SqlParser._
import anorm._
import common.formatters.DateTimeFormatter.dateTimeFormatter
import domain.Categoria.Categoria
import domain.Status.Status
import org.joda.time.DateTime
import play.api.Logger
import play.api.db.Database
import play.api.libs.json.Json

case class ItemDeMenu(
  id: Long,
  status: Status,
  categoria: Categoria,
  nombre: String,
  descripcion: String,
  precio: Double,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime)

object ItemDeMenuRepo {
  implicit val itemDeMenuFormat = Json.format[ItemDeMenu]
}

class ItemDeMenuRepo @Inject()(db: Database) {

  val tableName = "ItemDeMenu"

  val parser: RowParser[ItemDeMenu] = {
    long("id") ~
      int("status") ~
      int("categoria") ~
      str("nombre") ~
      str("descripcion") ~
      double("precio") ~
      get[DateTime]("fecha_creacion") ~
      get[DateTime]("fecha_ultima_modificacion") map {
      case id ~ status ~ categoria ~ nombre ~ descripcion ~ precio ~ fechaCreacion ~ fechaUltimaModificacion =>
        ItemDeMenu(id, Status(status), Categoria(categoria), nombre, descripcion, precio, fechaCreacion, fechaUltimaModificacion)
    }
  }

  def findAll: List[ItemDeMenu] = {
    val selectQuery = s"SELECT * FROM $tableName"
    db.withConnection { implicit connection =>
      SQL(selectQuery).as(parser.*)
    }
  }

  def findByMenu(idMenu: Long): List[ItemDeMenu] = {
    val selectQuery = s"SELECT i.* FROM $tableName i " +
      s"JOIN Menu_ItemDeMenu mim ON mim.id_item_de_menu = i.id " +
      s"WHERE id_menu = {idMenu}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('idMenu -> idMenu).as(parser.*)
    }
  }

  def findById(id: Long): Option[ItemDeMenu] = {
    Logger.trace(s"find by id #$id in table: $tableName")
    val selectQuery = s"SELECT * FROM $tableName WHERE id = {id}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('id -> id).as(parser.singleOpt)
    }
  }

  def create(item: ItemDeMenu): Either[String, ItemDeMenu] = {
    db.withConnection { implicit connection =>
      val id: Option[Long] = SQL(s"insert into $tableName (status, categoria, nombre, descripcion, precio,  fecha_creacion, fecha_ultima_modificacion) " +
        s"values ({status}, {categoria}, {nombre}, {descripcion}, {precio}, {fechaCreacion}, {fechaUltimaModificacion})"
      ).on('status -> item.status.id, 'categoria -> item.categoria.id, 'nombre -> item.nombre, 'descripcion -> item.descripcion,
        'precio -> item.precio, 'fechaCreacion -> item.fechaCreacion, 'fechaUltimaModificacion -> item.fechaUltimaModificacion)
        .executeInsert()
      id.flatMap(findById).toRight("itemDeMenu.create.error")
    }
  }
}
