package com.melalex.monorail.health

import com.melalex.monorail.config.InfrastructureComponents
import com.melalex.monorail.health.mappers.{HealthCheckResultMapper, SubSystemHealthMapper}
import com.melalex.monorail.health.routes.HealthRouteProvider
import com.melalex.monorail.health.services.HealthService
import com.melalex.monorail.health.services.impl.SensorBackedHealthService
import com.melalex.monorail.health.services.sensors.{MonorailSensor, Sensor}
import com.melalex.monorail.util.RouteProvider
import com.softwaremill.macwire.{wire, wireSet}

trait HealthComponents extends InfrastructureComponents {

  // Sensors
  lazy val monorailSensor: Sensor = wire[MonorailSensor]
  lazy val sensors: Set[Sensor]   = wireSet[Sensor]

  // Mappers
  lazy val healthCheckResultMapper: HealthCheckResultMapper = wire[HealthCheckResultMapper]
  lazy val subSystemHealthMapper: SubSystemHealthMapper     = wire[SubSystemHealthMapper]

  // Services
  lazy val healthService: HealthService = wire[SensorBackedHealthService]

  // Routes
  lazy val healthRouteProvider: RouteProvider = wire[HealthRouteProvider]
}
