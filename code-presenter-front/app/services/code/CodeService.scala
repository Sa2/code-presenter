package services.code

import com.google.inject.Inject
import model.domain.code.entity.Code
import model.domain.code.repository.CodeRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by Sa2 on 2016/08/28.
  */
class CodeService @Inject()(codeRepository: CodeRepository) {

  def sendCode(code: Code) = {
    Future {
      codeRepository.storeToRedis(code)
    }.map { result =>
      codeRepository.resolveFromRedis()
    }
  }
}
