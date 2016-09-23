package code_presenter.stream.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.model.ws.Message
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Source}
import code_presenter.stream.websocket.Presentation
import model.domain.code.entity.Code

/**
  * Created by Sa2 on 2016/09/19.
  */
class BroadcastActor extends Actor with ActorLogging {
  import BroadcastActor._
  def receive() = {
    case Broadcast(code) =>

//      Presentetion.PresentationActor ! code
      println("called")
      println(code.content)
  }
}

object BroadcastActor {

  val props = Props[BroadcastActor]
  case class Broadcast(code: Code)
}