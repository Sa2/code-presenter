package clients.mysql

import clients.Client

/**
  * Created by Sa2 on 2016/08/21.
  */
class MysqlClient extends Client {

}

object MysqlClient {

  def apply: MysqlClient = new MysqlClient()
}
