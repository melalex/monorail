package com.melalex.monorail

import akka.http.scaladsl.Http
import com.melalex.monorail.config.AppComponents

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App with AppComponents {

  val bindingFuture = Http()
    .bindAndHandle(compositeRouterProvider.provideRoute, serverProperties.host, serverProperties.port)

  Await.result(system.whenTerminated, Duration.Inf)
}
