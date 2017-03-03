package domain

import javax.inject.Inject

import anorm.{RowParser, ~}
import anorm.SqlParser._
import domain.EstadoItemDePedido.EstadoItemDePedido
import domain.Status.Status
import org.joda.time.DateTime
import play.api.db.Database

case class ItemDePedido(
  id: Long,
  pedido: Pedido,
  status: Status,
  estado: EstadoItemDePedido,
  cantidad: Int,
  comentario: String,
  abonado: Boolean,
  fechaUltimaModificacion: DateTime,
  idUsuarioUltimaModificacion: Long,
  itemDeMenu: ItemDeMenu)

class ItemDePedidoRepo @Inject()(db: Database, pedidoRepo: PedidoRepo, itemDeMenuRepo: ItemDeMenuRepo) {

  val tableName = "ItemDePedido"

  val parser: RowParser[ItemDePedido] = {
      long("id") ~
      long("id_pedido") ~
      int("status") ~
      int("estado") ~
      int("cantidad") ~
      str("comentario") ~
      bool("abonado")~
      get[DateTime]("fecha_ultima_modificacion") ~
      long("id_usuario_ultima_modificacion") ~
      long("id_item_de_menu")  map {
      case id ~ idPedido ~ status ~ estado ~ cantidad ~ comentario ~ abonado ~ fechaUltimaModificacion ~ idUsuarioUltimaModificacion ~ idItemDeMenu =>
        ItemDePedido(id, pedidoRepo.findById(idPedido).getOrElse(throw new RuntimeException("pedido.reference.not.found")), Status(status),
                     EstadoItemDePedido(estado), cantidad, comentario, abonado, fechaUltimaModificacion, idUsuarioUltimaModificacion,
                     itemDeMenuRepo.findById(idItemDeMenu).getOrElse(throw new RuntimeException("itemDeMenu.reference.not.found")))
    }
  }

}
