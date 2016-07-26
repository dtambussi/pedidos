package models.burns

import anorm.SqlParser._
import anorm._
import play.api.Logger
import play.api.db.Database

object Roles {
  val AdminRole = "ADMIN"
}

case class Role(
  id: Long,
  name: String
)

class RoleRepo(val db: Database) {

  val roles = "roles"
  val userRoles = "user_roles"

  def parser: RowParser[Role] = {
    long("id") ~
    str("name") map {
      case id ~ name => Role(id, name)
    }
  }

  final val findByUserIdQuery =  s"""SELECT * FROM $roles br
                                     INNER JOIN $userRoles bur ON br.id = bur.role_id
                                     WHERE user_id = {userId} AND br.is_deleted = false"""

  def findByUserId(userId: Long): List[Role] = {
    Logger.debug(s"Find user roles by user id: $userId")
    db.withConnection { implicit connection =>
      SQL(findByUserIdQuery).on('userId -> userId).as(parser.*)
    }
  }
}