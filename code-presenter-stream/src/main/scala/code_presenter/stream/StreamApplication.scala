package code_presenter.stream

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import code_presenter.stream.actors.EventFetchActor
import code_presenter.stream.websocket.{EchoService, MainService, WebSocketServer}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Sa2 on 2016/09/18.
  */
object StreamApplication extends App {
//  val pingActor = system.actorOf(PingActor.props, "pingActor")
  implicit val actorSystem = ActorSystem("akka-system")
  implicit val flowMaterializer = ActorMaterializer()
//  val eventFetchActor = actorSystem.actorOf(EventFetchActor.props, "eventFetchActor")

  val interface = "localhost"
  val port = 8080

  val service = new WebSocketServer()

  val binding = Http().bindAndHandle(service.route, interface, port)
  println(s"Server is now online at http://$interface:$port\n")

  //  pingActor ! PingActor.Initialize
//  Future {
//    while(true) {
//      eventFetchActor ! EventFetchActor.EventFetch
//      Thread.sleep(1000)
//    }
//  }

  // This example app will ping pong 3 times and thereafter terminate the ActorSystem -
  // see counter logic in PingActor
//  system.awaitTermination()
}
