package com.melalex.monorail.error

import akka.http.scaladsl.server.directives.RouteDirectives
import com.softwaremill.macwire.wire

trait ErrorComponents {

  val problemDirectives: RouteDirectives = wire[RouteDirectives]
}
