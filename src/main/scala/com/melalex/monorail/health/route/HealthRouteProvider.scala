package com.melalex.monorail.health.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.health.dto.HealthCheckResultDto
import com.melalex.monorail.health.service.HealthService
import com.melalex.monorail.health.transformer.HealthTransformations
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.scalaland.chimney.dsl.TransformerOps

import scala.concurrent.ExecutionContext

class HealthRouteProvider(healthService: HealthService)(implicit executionContext: ExecutionContext)
    extends RouteProvider
    with FailFastCirceSupport
    with HealthTransformations {

  override def provideRoute: Route =
    path("health") {
      get {
        complete(healthService.checkHealth.map(_.transformInto[HealthCheckResultDto]))
      }
    }
}
