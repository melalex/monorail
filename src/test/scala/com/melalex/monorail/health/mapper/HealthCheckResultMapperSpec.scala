package com.melalex.monorail.health.mapper

import com.melalex.monorail.BaseUnitTestSpec
import com.melalex.monorail.fixtures.HealthCheckFixture
import com.softwaremill.macwire.wire

class HealthCheckResultMapperSpec extends BaseUnitTestSpec {

  val subSystemHealthMapper: SubSystemHealthMapper     = mock[SubSystemHealthMapper]
  val healthCheckResultMapper: HealthCheckResultMapper = wire[HealthCheckResultMapper]

  "HealthCheckResultMapper" should "map HealthCheckResult to DTO" in new HealthCheckFixture {
    subSystemHealthMapper.apply _ expects model.healthyMonorail() returning dto.healthyMonorail()

    healthCheckResultMapper(model.healthy()) shouldEqual dto.healthy()
  }
}
