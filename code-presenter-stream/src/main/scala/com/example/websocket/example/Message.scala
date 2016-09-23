package com.example.websocket.example

import akka.stream.javadsl
import akka.stream.scaladsl.Source
import akka.util.ByteString

/**
  * Created by Sa2 on 2016/09/18.
  */
sealed trait Message extends akka.http.javadsl.model.ws.Message

//sealed trait TextMessage extends akka.http.javadsl.model.ws.TextMessage with Message {
//  /**
//    * The contents of this message as a stream.
//    */
//  def textStream: Source[String, _]
//
//  /** Java API */
//  override def getStreamedText: javadsl.Source[String, _] = textStream.asJava
//  override def asScala: TextMessage = this
//}
//
//sealed trait BinaryMessage extends akka.http.javadsl.model.ws.BinaryMessage with Message {
//  /**
//    * The contents of this message as a stream.
//    */
//  def dataStream: Source[ByteString, _]
//
//  /** Java API */
//  override def getStreamedData: javadsl.Source[ByteString, _] = dataStream.asJava
//  override def asScala: BinaryMessage = this
//}

