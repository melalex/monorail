package com.melalex.monorail.config

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait AkkaComponents {

  implicit val system: ActorSystem = ActorSystem("monorail")
  implicit val executor: ExecutionContext = system.dispatcher
}
