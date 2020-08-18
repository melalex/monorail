package com.melalex.monorail.util

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class CompositeRouteProvider(val delegates: Set[RouteProvider]) {

  def provideRoute: Route =
    delegates
      .map(_.provideRoute)
      .reduce((r1, r2) => r1 ~ r2)
}
