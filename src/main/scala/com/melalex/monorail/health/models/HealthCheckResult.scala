package com.melalex.monorail.health.models

case class HealthCheckResult(subSystems: Set[SubSystemHealth]) {

  def isOk: Boolean = subSystems.map(_.status).forall(_.isOk)

  def isNotOk: Boolean = !isOk
}
