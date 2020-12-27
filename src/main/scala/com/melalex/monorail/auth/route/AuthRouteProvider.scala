package com.melalex.monorail.auth.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.melalex.monorail.auth.dto.LoginDto
import com.melalex.monorail.auth.model.AuthProvider
import com.melalex.monorail.auth.service.AuthService
import com.melalex.monorail.session.UserSessionDirectives
import com.melalex.monorail.session.model.UserSession
import com.melalex.monorail.user.transformer.UserTransformations
import com.melalex.monorail.util.RouteProvider
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.scalaland.chimney.dsl.TransformerOps

class AuthRouteProvider(
    userSessionDirectives: UserSessionDirectives,
    authService: AuthService
) extends RouteProvider
    with FailFastCirceSupport
    with UserTransformations {

  import authService._
  import userSessionDirectives._

  private val providers = Map("google" -> AuthProvider.Google)
    .withDefaultValue(AuthProvider.None)

  override def provideRoute: Route = path("login" / providers) { authProvider =>
    post {
      entity(as[LoginDto]) { login =>
        onSuccess(authenticate(authProvider, login.code)) { value =>
          setUserSession(value.transformInto[UserSession]) {
            complete(StatusCodes.OK)
          }
        }
      }
    }
  }
}
