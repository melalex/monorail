package com.melalex.monorail.health

import com.melalex.monorail.BaseRouteSpec
import com.melalex.monorail.config.AppComponents
import com.melalex.monorail.fixtures.HealthCheckResults
import com.melalex.monorail.health.dto.HealthCheckResultDto
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

class HealthComponentsTest extends BaseRouteSpec with AppComponents with FailFastCirceSupport {

  "/health endpoint" should "respond with OK" in new HealthCheckResults.Dto {
    Get("/api/v1/health") ~> routes ~> check {
      responseAs[HealthCheckResultDto] shouldEqual healthyDto()
    }
  }
}
