package com.melalex.monorail.health.dto

sealed trait SubSystemHealthDto {

  def name: String

  def status: String
}

case class HealthySubSystemDto(name: String) extends SubSystemHealthDto {

  val status: String = "OK"
}

case class UnHealthySubSystemDto(name: String, reason: String) extends SubSystemHealthDto {

  val status: String = "KO"
}
