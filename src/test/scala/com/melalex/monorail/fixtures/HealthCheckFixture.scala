package com.melalex.monorail.fixtures

import com.melalex.monorail.health.dto.{HealthCheckResultDto, SubSystemHealthDto}
import com.melalex.monorail.health.model.{HealthCheckResult, HealthCheckStatus, SubSystemHealth}

trait HealthCheckFixture {

  private val monorailSensorName = "MONORAIL"
  private val okResult           = "OK"

  def model: Model = new Model

  def dto: Dto = new Dto

  class Model {

    def healthy(): HealthCheckResult = HealthCheckResult(Set(healthyMonorail()))

    def healthyMonorail(): SubSystemHealth =
      SubSystemHealth(monorailSensorName, HealthCheckStatus.Ok)
  }

  class Dto {

    def healthy(): HealthCheckResultDto =
      HealthCheckResultDto(healthy = true, Set(healthyMonorail()))

    def healthyMonorail(): SubSystemHealthDto =
      SubSystemHealthDto(monorailSensorName, okResult, Option.empty)
  }
}
