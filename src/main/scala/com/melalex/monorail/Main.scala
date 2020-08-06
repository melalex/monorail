package com.melalex.monorail

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.melalex.monorail.config.AppComponents

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

object Main extends App with AppComponents {

  implicit val system: ActorSystem = ActorSystem("monorail")
  implicit val executor: ExecutionContext = system.dispatcher

  val bindingFuture = Http()
    .bindAndHandle(compositeRouterProvider.provideRoute, serverProperties.host, serverProperties.port)

  Await.result(system.whenTerminated, Duration.Inf)
}
