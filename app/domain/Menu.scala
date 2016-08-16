package domain

import org.joda.time.DateTime

case class Menu(id: Long,
  status: Status,
  nombre: String,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime,
  vigente: Boolean,
  items: Seq[ItemDeMenu] = Nil)

case class MenuItemDeMenu(idMenu: Long, idItemDeMenu: Long)
