package com.melalex.monorail.health.sensors

import com.melalex.monorail.health.DependencyHealth

import scala.concurrent.{ExecutionContext, Future}

object MonorailSensor {

  val SensorName = "MONORAIL"
}

class MonorailSensor(implicit val executionContext: ExecutionContext) extends Sensor {

  override def checkHealth: Future[DependencyHealth] = Future {
    DependencyHealth.ok(MonorailSensor.SensorName)
  }
}
