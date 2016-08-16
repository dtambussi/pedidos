package domain

import org.joda.time.DateTime

case class Sugerencia(
  id: Option[Long],
  status: Status,
  estado: EstadoSugerencia,
  nombre: String,
  descripcion: String,
  precio: Double,
  cantidadDisponible: Long,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime,
  fechaInicio: DateTime,
  fechaFin: DateTime,
  idItemDeMenu: Long,
  itemDeMenu: Option[ItemDeMenu] = None)
