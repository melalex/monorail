package com.melalex.monorail.health

import com.melalex.monorail.BaseRouteTestSpec
import com.melalex.monorail.fixtures.HealthCheckFixture
import com.melalex.monorail.health.dto.HealthCheckResultDto
import io.circe.generic.auto._

class HealthComponentsSpec extends BaseRouteTestSpec {

  "/health endpoint" should "respond with OK" in new HealthCheckFixture {
    Get("/api/v1/health") ~> routes ~> check {
      responseAs[HealthCheckResultDto] shouldEqual dto.healthy()
    }
  }
}
