package com.melalex.monorail.health

object HealthStatus extends Enumeration {
  type HealthStatus = Value

  val Ok, Unavailable = Value
}
