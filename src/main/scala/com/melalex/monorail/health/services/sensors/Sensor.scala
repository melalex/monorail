package com.melalex.monorail.health.services.sensors

import com.melalex.monorail.health.models.SubSystemHealth

import scala.concurrent.Future

trait Sensor {

  def checkHealth: Future[SubSystemHealth]
}
