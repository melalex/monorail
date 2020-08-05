package com.melalex.monorail.health

import com.melalex.monorail.health.HealthStatus.{HealthStatus, Ok, Unavailable}

object DependencyHealth {

  def ok(name: String): DependencyHealth = DependencyHealth(name, Ok)

  def unavailable(name: String): DependencyHealth = DependencyHealth(name, Unavailable)
}

case class DependencyHealth(name: String, status: HealthStatus)
