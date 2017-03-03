package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import services.TestDataCreatorService

class TestDataController @Inject()(createTestDataService: TestDataCreatorService) extends Controller {

  def createTestData() : Action[AnyContent] = Action {
    createTestDataService.createTestData()
    Ok(Json.obj("result" -> "ok"))
  }

}
