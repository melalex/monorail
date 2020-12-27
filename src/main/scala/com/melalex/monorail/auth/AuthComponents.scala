package com.melalex.monorail.auth

import akka.actor.ActorSystem
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.melalex.monorail.auth.model.AuthProvider
import com.melalex.monorail.auth.route.AuthRouteProvider
import com.melalex.monorail.auth.service.impl.AuthServiceImpl
import com.melalex.monorail.auth.service.verifier.impl.{GoogleTokenVerifier, NoneTokenVerifier}
import com.melalex.monorail.config.property.{GoogleProperties, MonorailProperties}
import com.melalex.monorail.session.UserSessionDirectives
import com.melalex.monorail.user.service.UserService
import com.melalex.monorail.util.RouteProvider
import com.softwaremill.macwire.wire

import scala.concurrent.ExecutionContext

trait AuthComponents {

  implicit def system: ActorSystem
  implicit def executor: ExecutionContext

  def googleProperties: GoogleProperties
  def monorailProperties: MonorailProperties
  def googleClientSecrets: GoogleClientSecrets
  def userSessionDirectives: UserSessionDirectives
  def userService: UserService

  // Routes
  lazy val authRouteProvider: RouteProvider = wire[AuthRouteProvider]

  // Services
  private[auth] lazy val authService = wire[AuthServiceImpl]

  // Verifier
  private[auth] lazy val googleTokenVerifier: GoogleTokenVerifier = wire[GoogleTokenVerifier]
  private[auth] lazy val noneTokenVerifier: NoneTokenVerifier     = wire[NoneTokenVerifier]

  private[auth] lazy val tokenVerifiers = Map(AuthProvider.Google -> googleTokenVerifier)
    .withDefaultValue(noneTokenVerifier)
}
