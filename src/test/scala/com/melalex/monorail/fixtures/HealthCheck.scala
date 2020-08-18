package com.melalex.monorail.fixtures

import akka.http.scaladsl.server.Route
import com.melalex.monorail.health.dto.HealthCheckResultDto

trait HealthCheck {

  def checkHealth(implicit routes: Route): HealthCheckResultDto = ???

  def dto = ???

  def model = ???
}
