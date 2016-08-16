package domain

import org.joda.time.DateTime

case class ItemDePedido(
  id: Option[Long] = None,
  idPedido: Option[Long] = None,
  status: Status,
  estado: EstadoItemDePedido,
  cantidad: Int,
  comentario: String,
  abonado: Boolean,
  fechaUltimaModificacion: DateTime,
  idUsuarioUltimaModificacion: Long,
  idItemDeMenu: Long,
  itemDeMenu: Option[ItemDeMenu] = None)
