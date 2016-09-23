package code_presenter.stream.websocket

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Status, Terminated}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}
import code_presenter.stream.actors.EventFetchActor

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Sa2 on 2016/09/23.
  */
trait Presentation {
  def presentationFlow(sender: String): Flow[String, Protocol.Message, Any]

  def injectMessage(message: Protocol.PresentationMessage): Unit
}

object Presentation {
  def create(system: ActorSystem): Presentation = {

    val presentationActor =
    system.actorOf(Props(new Actor {
      var subscribers = Set.empty[(String, ActorRef)]

      def receive: Receive = {
        case NewParticipant(name, subscriber) ⇒
          context.watch(subscriber)
          subscribers += (name -> subscriber)
          dispatch(Protocol.Joined(name, members))
//        case msg: ReceivedMessage      ⇒ dispatch(msg.toPresentationMessage)
        case msg: Protocol.PresentationMessage ⇒ dispatch(msg)
        case ParticipantLeft(person) ⇒
          val entry @ (name, ref) = subscribers.find(_._1 == person).get
          // report downstream of completion, otherwise, there's a risk of leaking the
          // downstream when the TCP connection is only half-closed
          ref ! Status.Success(Unit)
          subscribers -= entry
          dispatch(Protocol.Left(person, members))
        case Terminated(sub) ⇒
          // clean up dead subscribers, but should have been removed when `ParticipantLeft`
          subscribers = subscribers.filterNot(_._2 == sub)
      }
//      def sendAdminMessage(msg: String): Unit = dispatch(Protocol.PresentationMessage("admin", msg))
      def dispatch(msg: Protocol.Message): Unit = subscribers.foreach(_._2 ! msg)
      def members = subscribers.map(_._1).toSeq
    }))

    // FIXME: here some rate-limiting should be applied to prevent single users flooding the presentation
    def presentationInSink(sender: String) = Sink.actorRef[PresentationEvent](presentationActor, ParticipantLeft(sender))

    new Presentation {
      def presentationFlow(sender: String): Flow[String, Protocol.PresentationMessage, Any] = {
        val in =
          Flow[String]
            .map(ReceivedMessage(sender, _))
            .to(presentationInSink(sender))

        val out =
        Source.actorRef[Protocol.PresentationMessage](1, OverflowStrategy.fail)
          .mapMaterializedValue(presentationActor ! NewParticipant(sender, _))

        Flow.fromSinkAndSource(in, out)
      }
      def injectMessage(message: Protocol.PresentationMessage): Unit = presentationActor ! message // non-streams interface
    }
  }

  private sealed trait PresentationEvent
  private case class NewParticipant(name: String, subscriber: ActorRef) extends PresentationEvent
  private case class ParticipantLeft(name: String) extends PresentationEvent
  private case class ReceivedMessage(sender: String, message: String) extends PresentationEvent {
    def toPresentationMessage: Protocol.PresentationMessage = Protocol.PresentationMessage(sender, message)
  }
}