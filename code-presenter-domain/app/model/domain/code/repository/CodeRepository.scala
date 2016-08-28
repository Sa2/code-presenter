package model.domain.code.repository

import clients.redis.RedisClient
import com.google.inject.Inject
import model.domain.code.entity.Code
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await

/**
  * Created by Sa2 on 2016/08/28.
  */
class CodeRepository @Inject()(val redisClient: RedisClient) {
  val latestCodeHashStr = "latestCode"
  def storeToRedis(code: Code) = {
    // TODO: こけた時のためにエラーハンドリングが必要
    redisClient.redis.set(latestCodeHashStr, code)
  }
  def resolveFromRedis() = {
    // TODO: こけた時のためにエラーハンドリングが必要
  // うまくいかない…
//    redisClient.redis.get[Code](latestCodeHashStr).map { result =>
//      println(result.get)
//    }
    redisClient.redis.get(latestCodeHashStr).map { result =>
      Code.byteStringFormatter.deserialize(result.get)
    }
  }

  def pingToRedis() = {
    val futurePong = redisClient.redis.ping()
    println("Ping sent!")
    futurePong.map(pong => {
      println(s"Redis replied with a $pong")
    })
    Await.result(futurePong, 5 seconds)
  }
}
