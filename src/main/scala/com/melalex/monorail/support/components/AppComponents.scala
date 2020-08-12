package com.melalex.monorail.support.components

import com.melalex.monorail.health.HealthComponents
import com.melalex.monorail.support.properties.ServerProperties
import com.melalex.monorail.support.{CompositeRouteProvider, ConfigLoader, RouteProvider}
import com.softwaremill.macwire.{wire, wireSet, wireWith}
import com.typesafe.config.Config

trait AppComponents extends HealthComponents {

  // Routes
  lazy val routeProviders: Set[RouteProvider] = wireSet[RouteProvider]
  lazy val compositeRouteProvider: CompositeRouteProvider = wire[CompositeRouteProvider]

  // Config
  lazy val config: Config = ConfigLoader.load

  // Properties
  lazy val serverProperties: ServerProperties = wireWith(ServerProperties.create _)
}
