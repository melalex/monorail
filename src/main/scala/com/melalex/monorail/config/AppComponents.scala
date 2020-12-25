package com.melalex.monorail.config

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.auth.AuthComponents
import com.melalex.monorail.config.property.MonorailProperties
import com.melalex.monorail.error.ErrorComponents
import com.melalex.monorail.health.HealthComponents
import com.melalex.monorail.i18n.I18nComponents
import com.melalex.monorail.session.SessionComponents
import com.melalex.monorail.user.UserComponents
import com.melalex.monorail.util.{CompositeRouteProvider, RouteProvider}
import com.softwaremill.macwire.{wire, wireSet}

trait AppComponents
    extends PureConfigComponents
    with GoogleComponents
    with I18nComponents
    with SessionComponents
    with ErrorComponents
    with HealthComponents
    with AuthComponents
    with UserComponents {

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
