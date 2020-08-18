package com.melalex.monorail.health.dto

object SubSystemHealthDto {

  def ok(name: String): SubSystemHealthDto = SubSystemHealthDto(name, "OK", None)

  def ko(name: String, reason: String): SubSystemHealthDto =
    SubSystemHealthDto(name, "KO", Some(reason))
}

case class SubSystemHealthDto(name: String, status: String, reason: Option[String])
