package com.melalex.monorail.health.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.health.mappers.HealthCheckResultMapper
import com.melalex.monorail.health.services.HealthService
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class HealthRouteProvider(
    private val healthService: HealthService,
    private val healthCheckResultMapper: HealthCheckResultMapper,
    private implicit val executionContext: ExecutionContext
) extends RouteProvider
    with FailFastCirceSupport {

  override def provideRoute: Route =
    path("health") {
      get {
        complete(healthService.checkHealth.map(healthCheckResultMapper).map(_.asJson))
      }
    }
}
