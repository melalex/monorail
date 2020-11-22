package com.melalex.monorail.session

import akka.http.scaladsl.server.{Directive0, Directive1}
import com.melalex.monorail.session.model.UserSession
import com.softwaremill.session.SessionDirectives.{invalidateSession, requiredSession, setSession}
import com.softwaremill.session.{RefreshTokenStorage, SessionManager}
import com.softwaremill.session.SessionOptions.{refreshable, usingHeaders}

import scala.concurrent.ExecutionContext

class UserSessionDirectives(
    sessionManager: SessionManager[UserSession],
    refreshTokenStorage: RefreshTokenStorage[UserSession],
    executionContext: ExecutionContext
) {

  val requiredUserSession: Directive1[UserSession] =
    requiredSession(refreshable(sessionManager, refreshTokenStorage, executionContext), usingHeaders)

  val invalidateUserSession: Directive0 =
    invalidateSession(refreshable(sessionManager, refreshTokenStorage, executionContext), usingHeaders)

  def setUserSession(session: UserSession): Directive0 =
    setSession(refreshable(sessionManager, refreshTokenStorage, executionContext), usingHeaders, session)
}
