package domain

// import java.sql.Connection
// import Status._
// import com.github.nscala_time.time.Imports._
import javax.inject.Inject

import play.api.db.Database
import anorm._
import anorm.JodaParameterMetaData._
import anorm.SqlParser._
// import play.api.libs.json._
import play.api.Logger
import org.joda.time.DateTime

case class ItemDeMenu(
  id: Long,
  status: Status,
  categoria: Categoria,
  nombre: String,
  descripcion: String,
  precio: Double,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime)

class ItemDeMenuRepo @Inject()(db: Database) {

  val tableName = "ItemDeMenu"

  val parser: RowParser[ItemDeMenu] = {
      long("id") ~
      int("status") ~
      int("categoria") ~
      str("nombre") ~
      str("descripcion") ~
      double("precio")~
      get[DateTime]("fecha_creacion") ~
      get[DateTime]("fecha_ultima_modificacion") map {
      case id ~ status ~ categoria ~ nombre ~ descripcion ~ precio ~ fechaCreacion ~ fechaUltimaModificacion =>
        ItemDeMenu(id, Status.valueOf(status), Categoria.valueOf(categoria), nombre, descripcion, precio, fechaCreacion, fechaUltimaModificacion)
    }
  }

  def findAll: List[ItemDeMenu] = {
    val selectQuery = s"SELECT * FROM $tableName"
    db.withConnection { implicit connection =>
      SQL(selectQuery).as(parser.*)
    }
  }

  def findById(id: Long): Option[ItemDeMenu] = {
    Logger.trace(s"find by id #$id in table: $tableName")
    val selectQuery = s"SELECT * FROM $tableName WHERE id = {id}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('id -> id).as(parser.singleOpt)
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

  def save(status: Status, categoria: Categoria, nombre: String, descripcion: String, precio: Double,
           fechaCreacion: DateTime, fechaUltimaModificacion: DateTime): Either[String, ItemDeMenu] = {
    db.withConnection { implicit connection =>
      val id: Option[Long]= SQL(s"insert into $tableName (status, categoria, nombre, descripcion, precio,  fecha_creacion, fecha_ultima_modificacion) " +
        s"values ({status}, {categoria}, {nombre}, {descripcion}, {precio}, {fechaCreacion}, {fechaUltimaModificacion})"
      ).on('status -> status.value, 'categoria -> categoria.value, 'nombre -> nombre, 'descripcion -> descripcion,
           'precio -> precio, 'fechaCreacion -> fechaCreacion, 'fechaUltimaModificacion -> fechaUltimaModificacion).
        executeInsert()
      id.flatMap(findById).toRight("error creating ItemDeMenu")
    }
  }

}
