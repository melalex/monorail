package com.melalex.monorail.health.service.sensors

import akka.dispatch.Futures
import com.melalex.monorail.health.model.SubSystemHealth

import scala.concurrent.Future

object MonorailSensor {

  val SensorName = "MONORAIL"
}

class MonorailSensor extends Sensor {

  override def checkHealth: Future[SubSystemHealth] =
    Futures.successful(SubSystemHealth.ok(MonorailSensor.SensorName))
}
