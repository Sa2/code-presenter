package code_presenter.stream.websocket

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

/**
  * Created by Sa2 on 2016/09/23.
  */
object MainService extends WebService {
  override def route: Route = pathEndOrSingleSlash {
    complete("Welcome to websocket server")
  }
}
