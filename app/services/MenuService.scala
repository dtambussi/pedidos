package services

import javax.inject.Inject

import domain.{Menu, MenuRepo}
import org.joda.time.DateTime
import com.github.nscala_time.time.Imports._

import scala.util.Try

sealed trait NoMenuResult
case class InvalidLatestMenuUpdateRefDate() extends NoMenuResult
case class MenuNotFound() extends NoMenuResult
case class NoNewMenuContentFound() extends NoMenuResult

class MenuService @Inject()(menuRepo: MenuRepo) {

  def getLatestMenu(fechaUltimaModificacionRef: Option[String]): Either[NoMenuResult, Menu] = {
    val latestActiveMenu = menuRepo.findLatestActiveMenu()
    val findLatestActiveMenuResult = latestActiveMenu.toRight(MenuNotFound())
    fechaUltimaModificacionRef.toRight()
      .fold(
        justGetLatestMenuScenario => findLatestActiveMenuResult,
        noContentUnlessShouldUpdateScenario => {
          val parseRefDateResult = fechaUltimaModificacionRef.map(tryParseLatestUpdateRefDate).getOrElse(throw new Exception("fechaUltimaModificacionRef.param.expected"))
          parseRefDateResult.map { date =>
            latestActiveMenu.filter(_.fechaUltimaModificacion.isEqual(date))
              .map(_ => Left(NoNewMenuContentFound())).getOrElse(findLatestActiveMenuResult)
          }.getOrElse(Left(InvalidLatestMenuUpdateRefDate()))
        }
      )
  }

  def tryParseLatestUpdateRefDate(fechaUltimaModificacionRef: String): Try[DateTime] = {
    Try(DateTime.parse(fechaUltimaModificacionRef))
  }

}
