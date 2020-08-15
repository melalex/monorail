package com.melalex.monorail

import akka.http.scaladsl.Http
import com.melalex.monorail.config.AppComponents

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Main extends App with AppComponents {

  Http()
    .newServerAt(serverProperties.host, serverProperties.port)
    .bindFlow(routes)
    .onComplete {
      case Success(binding) =>
        system.log.info("Bound: {}", binding)
      case Failure(error) =>
        system.log.error(error, "Failed to bind. Shutting down")
        system.terminate()
    }

  Await.result(system.whenTerminated, Duration.Inf)
}
