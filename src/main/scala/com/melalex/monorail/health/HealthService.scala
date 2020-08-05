package com.melalex.monorail.health

import scala.concurrent.Future

trait HealthService {

  def checkHealth: Future[HealthCheckResult]
}
