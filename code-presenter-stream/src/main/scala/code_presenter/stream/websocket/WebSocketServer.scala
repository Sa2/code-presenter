package code_presenter.stream.websocket

import java.util.Date

import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import code_presenter.stream.actors.EventFetchActor
import code_presenter.stream.websocket.Protocol.PresentationMessage
import model.domain.code.entity.Code
import upickle._


/**
  * Created by Sa2 on 2016/09/19.
  */
class WebSocketServer(implicit fm: Materializer, system: ActorSystem) extends Directives {
  implicit val timeout = Timeout(500.milliseconds)
  val thePresentation = Presentation.create(system)
  val eventFetchActor = system.actorOf(EventFetchActor.props, "eventFetchActor")
  import system.dispatcher
  system.scheduler.schedule(500.milliseconds, 500.milliseconds) {
    val newCode = eventFetchActor ? EventFetchActor.IsNewCode
    newCode.onSuccess {
      case true =>
        val code = eventFetchActor ? EventFetchActor.EventFetch
        code.onSuccess {
          case code: Code =>
            thePresentation.injectMessage(PresentationMessage(sender = "presenter", s"${code.content}"))
          case msg: String => println(msg)
        }
        code.onFailure {
          case _ => println("fetch failed...")
        }
    }
  }

  def route =
    get {
      path("chat") {
        parameter('name) { name ⇒
          handleWebSocketMessages(websocketPresentationFlow(sender = name))
        }
      }
    }

  def websocketPresentationFlow(sender: String): Flow[Message, Message, Any] =
    Flow[Message]
      .collect {
        case TextMessage.Strict(msg) ⇒ msg // unpack incoming WS text messages...
        // This will lose (ignore) messages not received in one chunk (which is
        // unlikely because chat messages are small) but absolutely possible
        // FIXME: We need to handle TextMessage.Streamed as well.
      }
      .via(thePresentation.presentationFlow(sender)) // ... and route them through the chatFlow ...
      .map {
      case msg: Protocol.Message ⇒
        TextMessage.Strict(write(msg)) // ... pack outgoing messages into WS JSON messages ...
    }
}

object WebSocketServer