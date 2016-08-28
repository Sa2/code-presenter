package model.domain.code.entity

import akka.util.ByteString
import play.api.libs.json.Json
import redis.ByteStringFormatter

/**
  * Created by Sa2 on 2016/08/28.
  */
case class Code(id: Long, uuid: String, refKey: String, content: String, destination: Int)

object Code {
  private val delimiter = ":|!!|:"
  implicit def jsonReads = Json.reads[Code]
  implicit def jsonWrites = Json.writes[Code]
  implicit val byteStringFormatter = new ByteStringFormatter[Code]{
    override def serialize(data: Code): ByteString = {
      ByteString(data.id + delimiter + data.uuid + delimiter + data.refKey + delimiter + data.content + delimiter + data.destination)
    }

    override def deserialize(bs: ByteString): Code = {
      val r = bs.utf8String.split(delimiter).toList
      Code(r(0).toLong, r(1), r(2), r(3), r(4).toInt)
    }
  }
}