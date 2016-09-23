package model.domain.code.service

import com.google.inject.Inject
import model.domain.code.entity.Code
import model.domain.code.repository.CodeRepository
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Sa2 on 2016/08/28.
  */
class CodeService @Inject()(val codeRepository: CodeRepository) {
  import CodeService._
  def sendCode(code: Code) = {
    Future {
      val setCode = Code(code.id, new DateTime().getMillis.toString, code.refKey, code.content, code.destination)
      codeRepository.storeCodeToRedis(setCode)
    }.map { result =>
      codeRepository.resolveCodeFromRedis().map { code =>
        println(code.content)
      }
    }
  }
}


object CodeService