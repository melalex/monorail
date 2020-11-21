package com.melalex.monorail.health.service

import com.melalex.monorail.health.model.HealthCheckResult

import scala.concurrent.Future

trait HealthService {

  def checkHealth: Future[HealthCheckResult]
}
