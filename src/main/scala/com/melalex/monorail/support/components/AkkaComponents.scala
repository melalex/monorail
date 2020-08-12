package com.melalex.monorail.support.components

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait AkkaComponents {

  implicit val system: ActorSystem = ActorSystem("monorail")
  implicit val executor: ExecutionContext = system.dispatcher
}
