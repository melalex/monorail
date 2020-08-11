package com.melalex.monorail.config

import com.melalex.monorail.config.properties.ServerProperties
import com.melalex.monorail.health.HealthComponents
import com.melalex.monorail.support.RouteProvider
import com.melalex.monorail.support.impl.CompositeRouterProvider
import com.softwaremill.macwire.{wire, wireSet, wireWith}
import com.typesafe.config.Config

trait AppComponents extends HealthComponents {

  // Routes
  lazy val healthRouteProviders: Set[RouteProvider] = wireSet[RouteProvider]
  lazy val compositeRouterProvider: RouteProvider = wire[CompositeRouterProvider]

  // Config
  val config: Config = ConfigLoader.load

  // Properties
  val serverProperties: ServerProperties = wireWith(ServerProperties.create _)
}
