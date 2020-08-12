package com.melalex.monorail.support.properties

import com.typesafe.config.Config

object ServerProperties {

  def create(config: Config): ServerProperties = ServerProperties(
    config.getString("server.http.host"),
    config.getInt("server.http.port")
  )
}

case class ServerProperties(host: String, port: Int)
