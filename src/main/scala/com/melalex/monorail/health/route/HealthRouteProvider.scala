package com.melalex.monorail.health.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.health.mapper.HealthCheckResultMapper
import com.melalex.monorail.health.service.HealthService
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class HealthRouteProvider(
    healthService: HealthService,
    healthCheckResultMapper: HealthCheckResultMapper
)(implicit executionContext: ExecutionContext)
    extends RouteProvider
    with FailFastCirceSupport {

  override def provideRoute: Route =
    path("health") {
      get {
        complete(healthService.checkHealth.map(healthCheckResultMapper.mapAToB))
      }
    }
}
