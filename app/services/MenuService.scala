package services

import javax.inject.Inject

import domain.{Menu, MenuRepo}
import org.joda.time.DateTime

class MenuService @Inject()(menuRepo: MenuRepo) {

  def getLatestMenu(fechaUltimaModificacionRef: Option[DateTime]): Either[String, Option[Menu]] = {
    (for {
        latestActiveMenu <- menuRepo.findLatestActiveMenu().toRight("").right
        alreadyUpToDate <- Right(fechaUltimaModificacionRef.exists(_.equals(latestActiveMenu.fechaUltimaModificacion))).right
        result <- Right(if (!alreadyUpToDate) Some(latestActiveMenu) else None).right
     } yield result).fold(error => Left(error), Right(_))
  }

}
