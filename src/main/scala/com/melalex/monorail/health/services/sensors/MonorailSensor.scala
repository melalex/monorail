package com.melalex.monorail.health.services.sensors

import com.melalex.monorail.health.models.SubSystemHealth

import scala.concurrent.{ExecutionContext, Future}

object MonorailSensor {

  val SensorName = "MONORAIL"
}

class MonorailSensor(implicit val executionContext: ExecutionContext) extends Sensor {

  override def checkHealth: Future[SubSystemHealth] = Future {
    SubSystemHealth.ok(MonorailSensor.SensorName)
  }
}
