package com.melalex.monorail.health.service.sensors

import com.melalex.monorail.health.model.SubSystemHealth

import scala.concurrent.Future

trait Sensor {

  def checkHealth: Future[SubSystemHealth]
}
