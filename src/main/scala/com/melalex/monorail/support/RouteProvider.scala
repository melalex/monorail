package com.melalex.monorail.support

import akka.http.scaladsl.server.Route

trait RouteProvider {

  def provideRoute: Route
}
