package com.melalex.monorail.fixtures

import com.melalex.monorail.health.dto.{HealthCheckResultDto, SubSystemHealthDto}
import com.melalex.monorail.health.models.{HealthCheckResult, HealthCheckStatus, SubSystemHealth}

object HealthCheckResults {

  private val monorailSensorName = "MONORAIL"
  private val okResult           = "OK"

  trait Model {

    def healthyModel(): HealthCheckResult = HealthCheckResult(Set(healthyMonorailModel()))

    def healthyMonorailModel(): SubSystemHealth =
      SubSystemHealth(monorailSensorName, HealthCheckStatus.Ok)
  }

  trait Dto {

    def healthyDto(): HealthCheckResultDto = HealthCheckResultDto(healthy = true, Set())

    def healthyMonorailDto(): SubSystemHealthDto =
      SubSystemHealthDto(monorailSensorName, okResult, Option.empty)
  }
}
