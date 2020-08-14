package com.melalex.monorail.health.mappers

import com.melalex.monorail.health.dto.SubSystemHealthDto
import com.melalex.monorail.health.models.{HealthCheckStatus, SubSystemHealth}
import com.melalex.monorail.support.CustomMapper

class SubSystemHealthMapper extends CustomMapper[SubSystemHealth, SubSystemHealthDto] {

  override def map(source: SubSystemHealth): SubSystemHealthDto =
    source.status match {
      case HealthCheckStatus.Ok         => SubSystemHealthDto.ok(source.name)
      case HealthCheckStatus.Ko(reason) => SubSystemHealthDto.ko(source.name, reason)
    }
}
