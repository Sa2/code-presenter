package code_presenter.stream.actors

import akka.actor.{Actor, ActorLogging, Props}


/**
  * Created by Sa2 on 2016/09/18.
  */
class PongActor extends Actor with ActorLogging {
  import PongActor._

  def receive = {
    case PingActor.PingMessage(text) =>
      log.info("In PongActor - received message: {}", text)
      sender() ! PongMessage("pong")
  }
}

object PongActor {
  val props = Props[PongActor]
  case class PongMessage(text: String)
}
