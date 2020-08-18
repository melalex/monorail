package com.melalex.monorail.server

import akka.actor.{ActorSystem, Terminated}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import com.melalex.monorail.config.properties.ServerProperties
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class Server(
    private val route: Route,
    private val serverProperties: ServerProperties,
    private implicit val system: ActorSystem,
    private implicit val executionContext: ExecutionContext
) {

  private val log = LoggerFactory.getLogger(classOf[Server])

  def run(): Unit = {
    Http()
      .newServerAt(serverProperties.host, serverProperties.port)
      .bindFlow(route)
      .onComplete {
        case Success(binding) => afterStart(binding)
        case Failure(error)   => onError(error)
      }
  }

  private def afterStart(binding: ServerBinding): Unit = {
    log.info("Server started on {}", binding.localAddress.toString)
  }

  private def onError(exception: Throwable): Future[Terminated] = {
    log.error("Failed to bind. Shutting down", exception)

    system.terminate()
  }
}
