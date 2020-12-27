package com.melalex.monorail.health

import akka.actor.ActorSystem
import com.melalex.monorail.health.route.HealthRouteProvider
import com.melalex.monorail.health.service.impl.SensorBackedHealthService
import com.melalex.monorail.health.service.sensors.{MonorailSensor, Sensor}
import com.melalex.monorail.util.RouteProvider
import com.softwaremill.macwire.{wire, wireSet}

import scala.concurrent.ExecutionContext

trait HealthComponents {

  implicit def system: ActorSystem
  implicit def executor: ExecutionContext

  // Routes
  lazy val healthRouteProvider: RouteProvider = wire[HealthRouteProvider]

  // Sensors
  private[health] lazy val monorailSensor = wire[MonorailSensor]
  private[health] lazy val sensors        = wireSet[Sensor]

  // Services
  private[health] lazy val healthService = wire[SensorBackedHealthService]
}
