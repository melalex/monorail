package com.melalex.monorail

import akka.actor.Terminated
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import com.melalex.monorail.config.{AkkaComponents, AppComponents}

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main extends App with AppComponents with AkkaComponents {

  Http()
    .newServerAt(serverProperties.host, serverProperties.port)
    .bindFlow(routes)
    .onComplete {
      case Success(binding) => afterStart(binding)
      case Failure(error)   => onError(error)
    }

  private def afterStart(binding: ServerBinding): Unit = {
    system.log.info("Server started on {}", binding.localAddress.toString)
  }

  private def onError(exception: Throwable): Future[Terminated] = {
    system.log.error("Failed to bind. Shutting down", exception)

    system.terminate()
  }
}
