package model.domain.code.entity

/**
  * Created by Sa2 on 2016/08/28.
  */
object Destination extends Enumeration {
  val broadcast = Value(0)
  val multicast = Value(1)
  val unicast = Value(2)
}
