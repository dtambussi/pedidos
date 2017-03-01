package services

import javax.inject.Inject

import domain.{Menu, MenuRepo}
import org.joda.time.DateTime
import com.github.nscala_time.time.Imports._

sealed trait NoMenuResult
case class MenuNotFound() extends NoMenuResult
case class NoNewMenuContentFound() extends NoMenuResult

class MenuService @Inject()(menuRepo: MenuRepo) {

  def getLatestMenu(fechaUltimaModificacionRef: Option[String]): Either[NoMenuResult, Menu] = {
    (for {
        fechaUltimaModificacionRef <- Right(fechaUltimaModificacionRef.map(DateTime.parse)).right
        latestActiveMenu <- menuRepo.findLatestActiveMenu().toRight(MenuNotFound()).right
        alreadyUpToDate <- Right(fechaUltimaModificacionRef.exists(_.equals(latestActiveMenu.fechaUltimaModificacion))).right
        result <- if (!alreadyUpToDate) Right(latestActiveMenu).right else Left(NoNewMenuContentFound()).right
     } yield result).fold(noNewMenu => Left(noNewMenu), Right(_))
  }

}
