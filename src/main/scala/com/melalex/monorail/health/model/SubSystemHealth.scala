package com.melalex.monorail.health.model

import com.melalex.monorail.health.model.HealthCheckStatus.{Ko, Ok}

object SubSystemHealth {

  def ok(name: String): SubSystemHealth = SubSystemHealth(name, Ok)

  def ko(name: String, reason: String): SubSystemHealth = SubSystemHealth(name, Ko(reason))
}

case class SubSystemHealth(name: String, status: HealthCheckStatus)
