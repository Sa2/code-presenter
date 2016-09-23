package code_presenter.stream.actors

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import code_presenter.stream.actors.EventFetchActor.EventFetch
import code_presenter.stream.websocket.Presentation
import com.google.inject.{AbstractModule, Inject}
import model.domain.code.entity.Code
import model.domain.code.repository.CodeRepository
import play.api.libs.concurrent.AkkaGuiceSupport

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Sa2 on 2016/09/19.
  */
class EventFetchActor extends Actor with ActorLogging {
  import EventFetchActor._

  val codeRepository = CodeRepository()
//  val broadcastActor = context.actorOf(BroadcastActor.props, "broadcastActor")

  def receive = {
    case EventFetch =>
      val originSender = sender
//      sender ! "sample"
      log.info("Received event fetch message")
      codeRepository.resolveCodeFromRedis().map { code =>
        // TODO: 受け取ったcodeの日付をsendedCodeというハッシュでRedisに保存および比較して送信するか判断
        originSender ! code
      }
    case IsNewCode =>
      val originSender = sender
      codeRepository.resolveCodeFromRedis().map { broadcastCode =>
        codeRepository.resolveLatestCodeFromRedis().map { latestCode =>
          println(s"latest code ${latestCode.uuid}")
          println(s"broadcast code ${broadcastCode.uuid}")
          if (broadcastCode.uuid != latestCode.uuid) {
            println(true)
            codeRepository.storeLatestCodeToRedis(broadcastCode)
            originSender ! true
          } else {
            originSender ! false
          }
        }.recover {
          case ex =>
            codeRepository.storeLatestCodeToRedis(broadcastCode)
            originSender ! true
        }
      }
  }
}

object EventFetchActor {

  val props = Props[EventFetchActor]
  case object EventFetch
  case object IsNewCode
}