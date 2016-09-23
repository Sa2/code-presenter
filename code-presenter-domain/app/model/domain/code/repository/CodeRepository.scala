package model.domain.code.repository

import clients.redis.RedisClient
import com.google.inject.Inject
import model.domain.code.entity.Code

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

/**
  * Created by Sa2 on 2016/08/28.
  */
class CodeRepository @Inject()() {
  import CodeRepository._

  val redisClient = RedisClient.redisClient
  val broadcastCodeHashStr = "broadcastCode"
  val latestCodeIdHashStr = "latestCode"
  def storeCodeToRedis(code: Code) = {
    // TODO: こけた時のためにエラーハンドリングが必要
    redisClient.set(broadcastCodeHashStr, code)
  }

  def storeLatestCodeToRedis(code: Code) = {
    redisClient.set(latestCodeIdHashStr, code)
  }

  def resolveCodeFromRedis(): Future[Code] = {
    // TODO: こけた時のためにエラーハンドリングが必要
    // うまくいかない…
    //    redisClient.redis.get[Code](latestCodeHashStr).map { result =>
    //      println(result.get)
    //    }
    redisClient.get(broadcastCodeHashStr).map { result =>
      Code.byteStringFormatter.deserialize(result.get)
    }
  }

  def resolveLatestCodeFromRedis(): Future[Code] = {
    // TODO: こけた時のためにエラーハンドリングが必要
    redisClient.get(latestCodeIdHashStr).map { result =>
      Code.byteStringFormatter.deserialize(result.get)
    }
  }

  def pingToRedis() = {
    val futurePong = redisClient.ping()
    println("Ping sent!")
    futurePong.map(pong => {
      println(s"Redis replied with a $pong")
    })
    Await.result(futurePong, 5 seconds)
  }
}

object CodeRepository {
  def apply(): CodeRepository = {

    new CodeRepository()
  }
}