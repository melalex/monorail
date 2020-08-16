package com.melalex.monorail.config

import akka.actor.ActorSystem
import pureconfig.{ConfigObjectSource, ConfigSource}

import scala.concurrent.ExecutionContext

trait InfrastructureComponents {

  implicit val system: ActorSystem        = ActorSystem("monorail")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val config: ConfigObjectSource = ConfigSource.default
}
