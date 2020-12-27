package com.melalex.monorail.health.transformer

import com.melalex.monorail.health.dto.{HealthCheckResultDto, SubSystemHealthDto}
import com.melalex.monorail.health.model.{HealthCheckResult, HealthCheckStatus, SubSystemHealth}
import io.scalaland.chimney.Transformer

trait HealthTransformations {

  implicit val healthCheckResultTransformer: Transformer[HealthCheckResult, HealthCheckResultDto] = source =>
    HealthCheckResultDto(source.isOk, source.subSystems.map(subSystemHealthTransformer.transform))

  implicit val subSystemHealthTransformer: Transformer[SubSystemHealth, SubSystemHealthDto] = source =>
    source.status match {
      case HealthCheckStatus.Ok         => SubSystemHealthDto.ok(source.name)
      case HealthCheckStatus.Ko(reason) => SubSystemHealthDto.ko(source.name, reason)
  }
}
