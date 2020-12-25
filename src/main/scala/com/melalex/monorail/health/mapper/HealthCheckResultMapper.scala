package com.melalex.monorail.health.mapper

import com.melalex.monorail.health.dto.HealthCheckResultDto
import com.melalex.monorail.health.model.HealthCheckResult
import com.melalex.monorail.util.CustomMapper

class HealthCheckResultMapper(private val subSystemHealthMapper: SubSystemHealthMapper)
    extends CustomMapper[HealthCheckResult, HealthCheckResultDto] {

  override def mapAToB(source: HealthCheckResult): HealthCheckResultDto =
    HealthCheckResultDto(source.isOk, source.subSystems.map(subSystemHealthMapper.mapAToB))
}
