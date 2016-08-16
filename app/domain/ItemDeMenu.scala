package domain

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
