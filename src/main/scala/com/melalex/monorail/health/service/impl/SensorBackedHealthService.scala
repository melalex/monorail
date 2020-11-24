package com.melalex.monorail.health.service.impl

import com.melalex.monorail.health.model.HealthCheckResult
import com.melalex.monorail.health.service.HealthService
import com.melalex.monorail.health.service.sensors.Sensor
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

class SensorBackedHealthService(sensors: Set[Sensor])(implicit executionContext: ExecutionContext) extends HealthService {

  private val log = LoggerFactory.getLogger(classOf[SensorBackedHealthService])

  override def checkHealth: Future[HealthCheckResult] =
    Future
      .sequence(sensors.map(_.checkHealth))
      .map(HealthCheckResult)
      .andThen {
        case Success(value) if value.isNotOk =>
          log.warn("Received unhealthy check response: {}", value)
      }
}
