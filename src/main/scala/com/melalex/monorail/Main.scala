package com.melalex.monorail

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.melalex.monorail.config.AppComponents

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Main extends App with AppComponents {

  val routes = pathPrefix("api" / "v1") {
    compositeRouteProvider.provideRoute
  }

  Http()
    .bindAndHandle(routes, serverProperties.host, serverProperties.port)
    .onComplete {
      case Success(sb) =>
        system.log.info("Bound: {}", sb)
      case Failure(t) =>
        system.log.error(t, "Failed to bind. Shutting down")
        system.terminate()
    }

  Await.result(system.whenTerminated, Duration.Inf)
}
