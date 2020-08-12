package com.melalex.monorail.health.dto

case class HealthCheckResultDto(healthy: Boolean, subSystems: Set[SubSystemHealthDto])
