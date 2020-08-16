package com.melalex.monorail.health.mappers

import com.melalex.monorail.health.dto.HealthCheckResultDto
import com.melalex.monorail.health.models.HealthCheckResult
import com.melalex.monorail.util.CustomMapper

class HealthCheckResultMapper(val subSystemHealthMapper: SubSystemHealthMapper)
    extends CustomMapper[HealthCheckResult, HealthCheckResultDto] {

  override def apply(source: HealthCheckResult): HealthCheckResultDto =
    HealthCheckResultDto(source.isOk, source.subSystems.map(subSystemHealthMapper))
}
