package com.melalex.monorail.health.mappers

import com.melalex.monorail.BaseUnitTestSpec
import com.melalex.monorail.fixtures.HealthCheckFixture

class SubSystemHealthMapperSpec extends BaseUnitTestSpec {

  private val subSystemHealthMapper = new SubSystemHealthMapper

  "SubSystemHealthMapper" should "map SubSystemHealth to DTO" in new HealthCheckFixture {
    subSystemHealthMapper(model.healthyMonorail()) shouldEqual dto.healthyMonorail()
  }
}
