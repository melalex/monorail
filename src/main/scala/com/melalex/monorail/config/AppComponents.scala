package com.melalex.monorail.config

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.config.properties.ServerProperties
import com.melalex.monorail.health.HealthComponents
import com.melalex.monorail.util.{CompositeRouteProvider, RouteProvider}
import com.softwaremill.macwire.{wire, wireSet}
import pureconfig.generic.auto._

trait AppComponents extends HealthComponents {

  // Routes
  lazy val routeProviders: Set[RouteProvider]             = wireSet[RouteProvider]
  lazy val compositeRouteProvider: CompositeRouteProvider = wire[CompositeRouteProvider]

  lazy val routes: Route = pathPrefix("api" / "v1") {
    compositeRouteProvider.provideRoute
  }

  // Properties
  lazy val serverProperties: ServerProperties = config
    .at("monorail.server.http")
    .loadOrThrow[ServerProperties]
}
