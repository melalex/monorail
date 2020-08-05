package com.melalex.monorail.health.models

import com.melalex.monorail.health.models.HealthStatus.{HealthStatus, Ok, Unavailable}

object SubSystemHealth {

  def ok(name: String): SubSystemHealth = SubSystemHealth(name, Ok)

  def unavailable(name: String): SubSystemHealth = SubSystemHealth(name, Unavailable)
}

case class SubSystemHealth(name: String, status: HealthStatus)
