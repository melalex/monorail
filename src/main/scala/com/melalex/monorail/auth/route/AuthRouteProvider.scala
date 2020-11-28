package com.melalex.monorail.auth.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Source
import com.melalex.monorail.auth.dto.GoogleLoginDto
import com.melalex.monorail.auth.service.AuthService
import com.melalex.monorail.session.UserSessionDirectives
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import sun.security.jgss.GSSUtil.login

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class AuthRouteProvider(
    userSessionDirectives: UserSessionDirectives,
    authService: AuthService
)(implicit executionContext: ExecutionContext)
    extends RouteProvider
    with FailFastCirceSupport {

  import authService._
  import userSessionDirectives._

  override def provideRoute: Route = path("login" / "google") {
    post {
      entity(as[GoogleLoginDto]) { login =>
        onComplete(authWithGoogle(login.code)) {
          case Success(value) =>
            setUserSession(value) {
              complete(value)
            }
          case Failure(exception) =>
        }
      }
    }
  }
}
