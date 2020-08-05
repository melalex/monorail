package com.melalex.monorail

import scala.concurrent.ExecutionContext

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("monorail")
  implicit val executor: ExecutionContext = system.dispatcher

}
