package code_presenter.stream.websocket

import akka.http.scaladsl.server.Route

/**
  * Created by Sa2 on 2016/09/23.
  */
trait WebService {
  def route: Route
}
