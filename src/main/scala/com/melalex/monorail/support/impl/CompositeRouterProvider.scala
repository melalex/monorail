package com.melalex.monorail.support.impl

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.support.RouteProvider

class CompositeRouterProvider(val delegates: Set[RouteProvider]) extends RouteProvider {

  override def provideRoute: Route = delegates
    .map(_.provideRoute)
    .reduce((r1, r2) => r1 ~ r2)
}
