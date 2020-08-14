package com.melalex.monorail.health.services.impl

import akka.actor.ActorSystem
import akka.event.Logging
import com.melalex.monorail.health.models.HealthCheckResult
import com.melalex.monorail.health.services.HealthService
import com.melalex.monorail.health.services.sensors.Sensor

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

class SensorBackedHealthService(val sensors: Set[Sensor])(
    implicit val executionContext: ExecutionContext,
    implicit val system: ActorSystem
) extends HealthService {

  private val log = Logging(system, classOf[SensorBackedHealthService])

  override def checkHealth: Future[HealthCheckResult] =
    Future
      .sequence(sensors.map(_.checkHealth))
      .map(HealthCheckResult)
      .andThen {
        case Success(value) if value.isNotOk =>
          log.warning("Received unhealthy check response: {}", value)
      }
}
