package domain

import org.joda.time.DateTime

case class Rol(
  id: Long,
  status: Status,
  nombre: String,
  descripcion: String,
  fechaCreacion: DateTime,
  fechaUltimaModificacion: DateTime)