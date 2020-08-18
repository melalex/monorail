package com.melalex.monorail.health.services.sensors

import akka.dispatch.Futures
import com.melalex.monorail.health.models.SubSystemHealth

import scala.concurrent.Future

object MonorailSensor {

  val SensorName = "MONORAIL"
}

class MonorailSensor extends Sensor {

  override def checkHealth: Future[SubSystemHealth] =
    Futures.successful(SubSystemHealth.ok(MonorailSensor.SensorName))
}
