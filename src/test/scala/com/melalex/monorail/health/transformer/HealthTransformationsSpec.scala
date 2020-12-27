package com.melalex.monorail.health.transformer

import com.melalex.monorail.BaseUnitTestSpec
import com.melalex.monorail.fixtures.HealthCheckFixture
import com.melalex.monorail.health.dto.{HealthCheckResultDto, SubSystemHealthDto}
import io.scalaland.chimney.dsl.TransformerOps

class HealthTransformationsSpec extends BaseUnitTestSpec with HealthTransformations {

  "HealthCheckResultMapper" should "map HealthCheckResult to DTO" in new HealthCheckFixture {
    model.healthy().transformInto[HealthCheckResultDto] shouldEqual dto.healthy()
  }

  "SubSystemHealthMapper" should "map SubSystemHealth to DTO" in new HealthCheckFixture {
    model.healthyMonorail().transformInto[SubSystemHealthDto] shouldEqual dto.healthyMonorail()
  }
}
