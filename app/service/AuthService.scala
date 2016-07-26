package service

import models.burns.{User, BurnsUserRepo}
import security.PasswordManager
import utils.RandomHelper
import utils.DateUtils.now
import security.Session.emptySessionId

class AuthService(userRepo: BurnsUserRepo,
                  passwordManager: PasswordManager) {

  val  loginUserNotFoundErrorMsg = "username.notFound"
  val sessionNotFound = "session.notFound"

  def loginUser(username: String, password: String): Either[String, User] = {
    (for {
      user <- userRepo.findByUsername(username).toRight(loginUserNotFoundErrorMsg).right
      _ <- passwordManager.checkPassword(password, user.password).right
      loggedUser <- userRepo.updateUserSessionData(user.id, RandomHelper.newUUID, now).right
    } yield loggedUser)
      .fold(error => Left(error), loggedUser => Right(loggedUser))
  }

  def logoutUser(sessionId: String) : Either[String, User] = {
    (for {
      user <- userRepo.findBySessionId(sessionId).toRight(sessionNotFound).right
      loggedOutUser <- userRepo.updateUserSessionData(user.id, emptySessionId, now).right
    } yield loggedOutUser)
      .fold(error => Left(error), loggedUser => Right(loggedUser))
  }

}
