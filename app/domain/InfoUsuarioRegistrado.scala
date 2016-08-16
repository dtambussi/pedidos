package domain

import org.joda.time.DateTime

case class InfoUsuarioRegistrado(
  idUsuario: Long,
  nombre: String,
  apellido: String,
  email: String,
  password: String,
  fechaNacimiento: DateTime)
