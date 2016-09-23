package code_presenter.stream.actors

import akka.actor.{Actor, ActorLogging, Props}


/**
  * Created by Sa2 on 2016/09/18.
  */
class PingActor extends Actor with ActorLogging {
  import PingActor._

  var counter = 0
  val pongActor = context.actorOf(PongActor.props, "pongActor")

  def receive = {
    case Initialize =>
      log.info("In PingActor - starting ping-pong")
      pongActor ! PingMessage("ping")
    case PongActor.PongMessage(text) =>
      log.info("In PingActor - received message: {}", text)
      counter += 1
      if (counter == 3) context.system.shutdown()
      else sender() ! PingMessage("ping")
  }
}

object PingActor {
  val props = Props[PingActor]
  case object Initialize
  case class PingMessage(text: String)
}
