package controllers

import javax.inject._

import com.github.nscala_time.time.Imports._
import domain.MenuRepo.menuFormat
import domain._
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import services.{MenuNotFound, MenuService, NoNewMenuContentFound}

class MenuController @Inject()(menuService: MenuService) extends Controller {

  def menu(fechaUltimaModificacionRef: Option[String]): Action[AnyContent] = Action {

    menuService.getLatestMenu(fechaUltimaModificacionRef) match {
      case Right(menu: Menu) => Ok(Json.toJson(menu))
      case Left(MenuNotFound()) => NotFound
      case Left(NoNewMenuContentFound()) => NoContent
      case _ => InternalServerError
    }
  }
}
