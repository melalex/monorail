package com.melalex.monorail.health

import com.melalex.monorail.health.routes.HealthRouteProvider
import com.melalex.monorail.health.services.HealthService
import com.melalex.monorail.health.services.impl.SensorBackedHealthService
import com.melalex.monorail.health.services.sensors.{MonorailSensor, Sensor}
import com.melalex.monorail.support.RouteProvider
import com.softwaremill.macwire.{wire, wireSet}

trait HealthComponents {

  // Sensors
  lazy val monorailSensor: Sensor = wire[MonorailSensor]
  lazy val sensors: Set[Sensor] = wireSet[Sensor]

  // Services
  lazy val healthService: HealthService = wire[SensorBackedHealthService]

  // Controllers
  lazy val healthRouteProvider: RouteProvider = wire[HealthRouteProvider]
}
