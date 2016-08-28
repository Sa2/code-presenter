package controllers

import com.google.inject.Inject
import model.domain.code.entity.Code
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}
import services.code.CodeService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Sa2 on 2016/08/21.
  */
class CodeController @Inject()(val codeService: CodeService) extends Controller {

  def codeSend = Action.async(parse.json) { implicit rs =>
    rs.body.validate[Code].map { code =>
      codeService.sendCode(code)
      Future {
        Ok(Json.obj("result" -> "success"))
      }
    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" ->"failure", "error" -> JsError.toJson(e)))
      }
    }
  }
}
