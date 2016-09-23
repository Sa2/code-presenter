package code_presenter.stream.websocket

/**
  * Created by Sa2 on 2016/09/23.
  */
object Protocol {
  sealed trait Message
  case class PresentationMessage(sender: String, message: String) extends Message
  case class Joined(member: String, allMembers: Seq[String]) extends Message
  case class Left(member: String, allMembers: Seq[String]) extends Message
}