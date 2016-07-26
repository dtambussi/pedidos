package models.burns

import anorm.JodaParameterMetaData._
import anorm.SqlParser._
import anorm._
import org.joda.time.DateTime
import play.api.Logger
import play.api.db.Database
import utils.DateUtils.now

case class User(
  id: Long,
  name: String,
  username: String,
  password: String,
  sessionId: String,
  sessionDate: DateTime,
  isDeleted: Boolean
)

class BurnsUserRepo(val db: Database) {

  val users = "burns_users"

  def parser: RowParser[User] = {
    long("id") ~
    str("name") ~
    str("username") ~
    str("password") ~
    str("session_id") ~
    get[DateTime]("session_date") ~
    bool("is_deleted") map {
      case id~name~username~password~sessionId~sessionDate~isDeleted => User(id, name, username, password, sessionId, sessionDate, isDeleted)
    }
  }

  final val findByIdQuery =  s"SELECT * FROM $users WHERE id = {id} AND is_deleted = false"

  def findById(userId: Long): User = {
    db.withConnection { implicit connection =>
      SQL(findByIdQuery).on('id -> userId).as(parser.single)
    }
  }

  def findByIdOpt(userId: Long): Option[User] = {
    db.withConnection { implicit connection =>
      SQL(findByIdQuery).on('id -> userId).as(parser.singleOpt)
    }
  }

  def findBySessionId(sessionId: String): Option[User] = {
    Logger.debug(s"Find user by session id: $sessionId")
    db.withConnection { implicit connection =>
      SQL(s"""
        SELECT *
        FROM $users
        WHERE session_id = {sessionId} AND is_deleted = false
          """).on('sessionId -> sessionId).as(parser.singleOpt)
    }
  }

  def findByUsername(username: String): Option[User] = {
    db.withConnection { implicit connection =>
      SQL(s"""
        SELECT *
        FROM $users
        WHERE username = {username} AND is_deleted = false
          """).on('username -> username).as(parser.singleOpt)
    }
  }

  def create(name: String, username: String, password: String): Option[User] = {
    db.withConnection { implicit connection =>
      val id: Option[Long] = SQL(s"""
          INSERT INTO $users(name, username, password, session_id, session_date, is_deleted, creation_date, last_modification_date)
          VALUES ({name}, {username}, {password}, NULL, NULL, false, $now, $now)
            """).on('name -> name, 'username -> username, 'password -> password).executeInsert()
      id.map(id => findById(id))
    }
  }

  def getAll: List[User] = {
    db.withConnection { implicit connection =>
      SQL(s"""
        SELECT *
        FROM $users
        WHERE is_deleted = false
          """).as(parser.*)
    }
  }

  def modify(userId: Long, name: String, username: String): Either[String, User] = {
    db.withConnection { implicit connection =>
      val result = SQL(s"""
        UPDATE $users
          SET name={name},
              username={username},
              last_modification_date=$now
          WHERE id = {userId} AND is_deleted = false
        """).on('userId -> userId, 'name -> name, 'username -> username).executeUpdate()
      result match {
        case 1 => Right(findById(userId))
        case _ => Left(s"There was an error trying to modify user #$userId")
      }
    }
  }

  def modify(userId: Long, password: String): Either[String, User] = {
    db.withConnection { implicit connection =>
      val result = SQL(s"""
        UPDATE $users
          SET password={password}
          WHERE id = {userId} AND is_deleted = false
        """).on('userId -> userId).executeUpdate()
      result match {
        case 1 => Right(findById(userId))
        case _ => Left(s"There was an error trying to modify password for user #$userId")
      }
    }
  }

  def delete(userId: Long): Option[String] = {
    db.withConnection { implicit connection =>
      SQL(s"UPDATE $users SET deleted=true WHERE id={userId}").on('userId -> userId).executeUpdate() match {
        case 1 => None
        case _ => Some(s"There was an error trying to delete user #$userId")
      }
    }
  }

  def updateUserSessionData(userId: Long, sessionId: String, sessionDate: DateTime): Either[String, User] = {
    db.withConnection { implicit connection =>
      val result = SQL(s"""
        UPDATE $users
          SET session_id = {sessionId},
              session_date = {sessionDate}
          WHERE id = {userId} AND is_deleted = false
        """).on('userId -> userId, 'sessionId -> sessionId, 'sessionDate -> sessionDate).executeUpdate()
      result match {
        case 1 => Right(findById(userId))
        case _ => Left(s"There was an error trying to update user session data, for user #$userId")
      }
    }
  }

}
