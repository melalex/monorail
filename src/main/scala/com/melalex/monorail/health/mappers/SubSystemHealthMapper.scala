package com.melalex.monorail.health.mappers

import com.melalex.monorail.health.dto.{HealthySubSystemDto, SubSystemHealthDto, UnHealthySubSystemDto}
import com.melalex.monorail.health.models.{HealthCheckStatus, SubSystemHealth}
import com.melalex.monorail.support.CustomMapper

class SubSystemHealthMapper extends CustomMapper[SubSystemHealth, SubSystemHealthDto] {

  override def map(source: SubSystemHealth): SubSystemHealthDto = source.status match {
    case HealthCheckStatus.Ok => HealthySubSystemDto(source.name)
    case HealthCheckStatus.Ko(reason) => UnHealthySubSystemDto(source.name, reason)
  }
}
