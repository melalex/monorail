package com.melalex.monorail.health.sensors

import com.melalex.monorail.health.DependencyHealth

import scala.concurrent.Future

trait Sensor {

  def checkHealth: Future[DependencyHealth]
}
