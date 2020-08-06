package com.melalex.monorail.health.services.sensors

import com.melalex.monorail.health.models.SubSystemHealth

import scala.concurrent.Future
import scala.concurrent.Promise._

object MonorailSensor {

  val SensorName = "MONORAIL"
}

class MonorailSensor extends Sensor {

  override def checkHealth: Future[SubSystemHealth] = successful(SubSystemHealth.ok(MonorailSensor.SensorName)).future
}
