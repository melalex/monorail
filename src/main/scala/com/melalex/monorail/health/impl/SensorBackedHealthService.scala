package com.melalex.monorail.health.impl

import com.melalex.monorail.health.sensors.Sensor
import com.melalex.monorail.health.{HealthCheckResult, HealthService}

import scala.concurrent.{ExecutionContext, Future}

class SensorBackedHealthService(val sensors: Seq[Sensor])(implicit val executionContext: ExecutionContext) extends HealthService {

  override def checkHealth: Future[HealthCheckResult] =
    Future.sequence(sensors.map(_.checkHealth)).map(HealthCheckResult)
}
