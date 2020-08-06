package com.melalex.monorail.health.models

import com.melalex.monorail.health.models.HealthStatus.{HealthStatus, Ko, Ok}

object SubSystemHealth {

  def ok(name: String): SubSystemHealth = SubSystemHealth(name, Ok)

  def ko(name: String): SubSystemHealth = SubSystemHealth(name, Ko)
}

case class SubSystemHealth(name: String, status: HealthStatus)
