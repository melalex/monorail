package com.melalex.monorail.health

import com.melalex.monorail.config.InfrastructureComponents
import com.melalex.monorail.health.mappers.{HealthCheckResultMapper, SubSystemHealthMapper}
import com.melalex.monorail.health.routes.HealthRouteProvider
import com.melalex.monorail.health.services.impl.SensorBackedHealthService
import com.melalex.monorail.health.services.sensors.{MonorailSensor, Sensor}
import com.melalex.monorail.util.RouteProvider
import com.softwaremill.macwire.{wire, wireSet}

trait HealthComponents extends InfrastructureComponents {

  // Routes
  lazy val healthRouteProvider: RouteProvider = wire[HealthRouteProvider]

  // Sensors
  private[health] lazy val monorailSensor = wire[MonorailSensor]
  private[health] lazy val sensors        = wireSet[Sensor]

  // Mappers
  private[health] lazy val healthCheckResultMapper = wire[HealthCheckResultMapper]
  private[health] lazy val subSystemHealthMapper   = wire[SubSystemHealthMapper]

  // Services
  private[health] lazy val healthService = wire[SensorBackedHealthService]
}
