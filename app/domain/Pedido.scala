package domain

import org.joda.time.DateTime

case class Pedido(id: Option[Long],
  status: Status,
  idMenu: Long,
  estado: EstadoPedido,
  comentario: String = "",
  abonado: Boolean,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime,
  idUsuario: Long,
  idUsuarioUltimaModificacion: Long,
  items: Seq[ItemDePedido] = Nil,
  mesa: Option[String] = None)

case class PedidoItemDePedido(idPedido: Long, idItemDePedido: Long)
