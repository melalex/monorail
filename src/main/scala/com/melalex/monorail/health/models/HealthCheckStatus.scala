package com.melalex.monorail.health.models

sealed abstract class HealthCheckStatus(val isOk: Boolean) {

  def isFailure: Boolean = !isOk
}

object HealthCheckStatus {

  case object Ok extends HealthCheckStatus(isOk = true)

  final case class Ko(reason: String) extends HealthCheckStatus(isOk = false)

}