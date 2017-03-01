package domain

import javax.inject.Inject

import anorm._
import anorm.SqlParser._
import domain.EstadoPedido.EstadoPedido
import domain.Status.Status
import org.joda.time.DateTime
import play.api.db.Database

case class Pedido(
  id: Long,
  status: Status,
  menu: Menu,
  estado: EstadoPedido,
  comentario: String = "",
  abonado: Boolean,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime,
  idUsuario: Long,
  idUsuarioUltimaModificacion: Long,
  items: Seq[ItemDePedido] = Nil,
  mesa: String = "")

class PedidoRepo @Inject()(db: Database, menuRepo: MenuRepo) {

  val tableName = "Pedido"

  val parser: RowParser[Pedido] = {
      long("id") ~
      int("status") ~
      long("id_menu") ~
      int("estado") ~
      str("comentario") ~
      bool("abonado") ~
      get[DateTime]("fecha_creacion") ~
      get[DateTime]("fecha_ultima_modificacion") ~
      long("id_usuario") ~
      long("id_usuario_ultima_modificacion")  map {
      case id ~ status ~ idMenu ~ estado ~ comentario ~ abonado ~ fechaCreacion ~ fechaUltimaModificacion ~ idUsuario ~ idUsuarioUltimaModificacion =>
        Pedido(id, Status(status), menuRepo.findById(idMenu).getOrElse(throw new RuntimeException("menu reference not found")),
               EstadoPedido(estado), comentario, abonado, fechaCreacion, fechaUltimaModificacion, idUsuario, idUsuarioUltimaModificacion)
    }
  }

  def findById(id: Long): Option[Pedido] = {
    val selectQuery = s"SELECT * FROM $tableName WHERE id = {id}"
    db.withConnection { implicit connection =>
      SQL(selectQuery).on('id -> id).as(parser.singleOpt)
    }
  }
}
