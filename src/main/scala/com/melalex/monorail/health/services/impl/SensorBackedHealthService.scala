package com.melalex.monorail.health.services.impl

import com.melalex.monorail.health.models.HealthCheckResult
import com.melalex.monorail.health.services.sensors.Sensor
import com.melalex.monorail.health.services.HealthService

import scala.concurrent.{ExecutionContext, Future}

class SensorBackedHealthService(val sensors: Set[Sensor])(implicit val executionContext: ExecutionContext) extends HealthService {

  override def checkHealth: Future[HealthCheckResult] =
    Future.sequence(sensors.map(_.checkHealth)).map(HealthCheckResult)
}
