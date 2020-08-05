package com.melalex.monorail.health.models

object HealthStatus extends Enumeration {
  type HealthStatus = Value

  val Ok, Unavailable = Value
}
