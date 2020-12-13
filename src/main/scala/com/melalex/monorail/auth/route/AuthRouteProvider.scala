package com.melalex.monorail.auth.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.melalex.monorail.auth.dto.GoogleLoginDto
import com.melalex.monorail.auth.service.AuthService
import com.melalex.monorail.session.UserSessionDirectives
import com.melalex.monorail.session.model.UserSession
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.scalaland.chimney.dsl.TransformerOps

import scala.concurrent.ExecutionContext

class AuthRouteProvider(
    userSessionDirectives: UserSessionDirectives,
    authService: AuthService
)(implicit executionContext: ExecutionContext, materializer: Materializer)
    extends RouteProvider
    with FailFastCirceSupport {

  import authService._
  import userSessionDirectives._

  override def provideRoute: Route = path("login" / "google") {
    post {
      entity(as[GoogleLoginDto]) { login =>
        onSuccess(authWithGoogle(login.code)) { value =>
          setUserSession(value.transformInto[UserSession]) {
            complete(value)
          }
        }
      }
    }
  }
}
