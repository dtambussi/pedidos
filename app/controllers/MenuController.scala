package controllers

import javax.inject._

import domain._
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import services.{MenuNotFound, MenuService, NoNewMenuContentFound}
import MenuRepo.menuFormat

class MenuController @Inject()(menuService: MenuService) extends Controller {

  def menu(fechaUltimaModificacionRef: Option[String]): Action[AnyContent] = Action {
    menuService.getLatestMenu(fechaUltimaModificacionRef) match {
      case Right(menu: Menu) => Ok(Json.toJson(menu))
      case Left(MenuNotFound()) => NotFound
      case Left(NoNewMenuContentFound()) => NoContent
      case _ => InternalServerError
    }
  }

//  def saveItemDeMenu(): Action[AnyContent] = Action {
//    println("saveItemDeMenu() _")
//    val now = DateTime.now(DateTimeZone.UTC)
//    repo.save(domain.Status.Active, Categoria.Cafeteria, "nombre-2", "desc", 10, now, now)
//    Ok("")
//  }

}
