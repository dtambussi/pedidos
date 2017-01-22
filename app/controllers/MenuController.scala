package controllers

import domain._
import play.api.mvc.{Action, AnyContent, Controller}
import javax.inject._

import org.joda.time.{DateTime, DateTimeZone}

class MenuController @Inject()(repo: ItemDeMenuRepo) extends Controller {

  def saveItemDeMenu(): Action[AnyContent] = Action {
    println("saveItemDeMenu() _")
    val now = DateTime.now(DateTimeZone.UTC)
    repo.save(domain.Status.Active, Categoria.Cafeteria, "nombre-2", "desc", 10, now, now)
    Ok("")
  }

}
