package controllers

import com.google.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Sa2 on 2016/08/21.
  */
class CodeController @Inject() extends Controller {

  def codeSend = Action.async { implicit rs =>
    Future {
      Ok(Json.obj("status" -> 200))
    }
  }

}
