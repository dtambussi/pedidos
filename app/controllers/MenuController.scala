package controllers

import domain._
import play.api.mvc.{Action, AnyContent, Controller}
import javax.inject._

import org.joda.time.{DateTime, DateTimeZone}
import play.api.db._

class MenuController  @Inject()(db: Database) extends Controller {

  def saveItemDeMenu(): Action[AnyContent] = Action {
    println("saveItemDeMenu() _")
    val repo = new ItemDeMenuRepo(db)
    val now = DateTime.now(DateTimeZone.UTC)
    repo.save(domain.Status.Active, Categoria.Bebidas, "nombre", "desc", 10, now, now)
    Ok("")
  }

}
