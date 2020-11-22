package com.melalex.monorail.session

import akka.actor.{ActorSystem, Scheduler}
import akka.http.scaladsl.server.{Directive0, Directive1}
import com.melalex.monorail.session.model.UserSession
import com.melalex.monorail.session.repository.UserSessionRepository
import com.melalex.monorail.session.repository.impl.FirestoreUserSessionRepository
import com.melalex.monorail.session.srvice.UserSessionRefreshTokenStorage
import com.softwaremill.macwire.wire
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._
import org.json4s.JValue

import scala.concurrent.ExecutionContext

trait SessionComponents {

  implicit def system: ActorSystem

  implicit def executor: ExecutionContext

  def setUserSession(v: UserSession): Directive0 = setSession(refreshable, usingHeaders, v)

  val requiredUserSession: Directive1[UserSession] = requiredSession(refreshable, usingHeaders)
  val invalidateUserSession: Directive0            = invalidateSession(refreshable, usingHeaders)

  private implicit val scheduler: Scheduler                                  = system.scheduler
  private[session] val userSessionRepository: UserSessionRepository          = wire[FirestoreUserSessionRepository]
  private implicit val refreshTokenStorage: RefreshTokenStorage[UserSession] = wire[UserSessionRefreshTokenStorage]
  private implicit val sessionConfig: SessionConfig                          = SessionConfig.fromConfig()
  private implicit val serializer: SessionSerializer[UserSession, JValue]    = JValueSessionSerializer.caseClass[UserSession]
  private implicit val encoder: SessionEncoder[UserSession]                  = new JwtSessionEncoder[UserSession]
  private implicit val sessionManager: SessionManager[UserSession]           = new SessionManager[UserSession](sessionConfig)
}
