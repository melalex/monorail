package com.melalex.monorail.health.sensors

import com.melalex.monorail.health.DependencyHealth

import scala.concurrent.Future

object MonorailSensor {

  val SensorName = "MONORAIL"
}

class MonorailSensor extends Sensor {

  override def checkHealth: Future[DependencyHealth] = Future {
    DependencyHealth.ok(MonorailSensor.SensorName)
  }
}
