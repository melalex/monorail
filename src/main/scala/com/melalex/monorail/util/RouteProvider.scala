package com.melalex.monorail.util

import akka.http.scaladsl.server.Route

trait RouteProvider {

  def provideRoute: Route
}
