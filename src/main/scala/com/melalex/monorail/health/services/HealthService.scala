package com.melalex.monorail.health.services

import com.melalex.monorail.health.models.HealthCheckResult

import scala.concurrent.Future

trait HealthService {

  def checkHealth: Future[HealthCheckResult]
}
