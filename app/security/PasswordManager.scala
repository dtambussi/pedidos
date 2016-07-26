package security

import org.mindrot.jbcrypt.BCrypt

class PasswordManager {

  val error = "incorrect.password"

  def encodePassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

  def checkPassword(candidate: String, hashed: String): Either[String, String] = {
    if (BCrypt.checkpw(candidate, hashed)) Right(candidate) else Left(error)
  }

}
