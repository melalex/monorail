package com.melalex.monorail.auth.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.auth.dto.GoogleLoginDto
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.ExecutionContext

class AuthRouteProvider(private implicit val executionContext: ExecutionContext) extends RouteProvider with FailFastCirceSupport {

  override def provideRoute: Route = path("login" / "google") {
    post {
      entity(as[GoogleLoginDto]) { login =>
        }
    }
  }
}
