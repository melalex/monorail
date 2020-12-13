package com.melalex.monorail.config

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.config.properties.{MonorailProperties, ServerProperties}
import com.melalex.monorail.health.HealthComponents
import com.melalex.monorail.util.{CompositeRouteProvider, RouteProvider}
import com.softwaremill.macwire.{wire, wireSet}

trait AppComponents extends PureConfigComponents with HealthComponents {

  lazy val routes: Route = pathPrefix("api" / "v1") {
    compositeRouteProvider.provideRoute
  }

  // Properties
  lazy val monorailProperties: MonorailProperties = config
    .at("monorail")
    .loadOrThrow[MonorailProperties]

  // Routes
  private[config] lazy val routeProviders         = wireSet[RouteProvider]
  private[config] lazy val compositeRouteProvider = wire[CompositeRouteProvider]
}
