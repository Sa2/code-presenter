package clients.redis

import clients.Client
import clients.redis.RedisConfig._
import redis.{RedisClient => RedisBaseClient}

/**
  * Created by Sa2 on 2016/08/21.
  */
class RedisClient extends Client {
  implicit val akkaSystem = akka.actor.ActorSystem()

  // TODO: Configの取り方を考えたいね
  val redis = RedisBaseClient(host, port)
}

object RedisClient {
  import RedisClient._
  def apply: RedisClient = new RedisClient()
}