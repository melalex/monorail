package com.melalex.monorail.session

import akka.actor.{ActorSystem, Scheduler}
import com.google.cloud.firestore.Firestore
import com.melalex.monorail.session.mapper.{DocumentSnapshotMapper, PersistentUserSessionMapper, RefreshTokenDataMapper}
import com.melalex.monorail.session.model.UserSession
import com.melalex.monorail.session.repository.UserSessionRepository
import com.melalex.monorail.session.repository.impl.FirestoreUserSessionRepository
import com.melalex.monorail.session.srvice.UserSessionRefreshTokenStorage
import com.softwaremill.macwire.wire
import com.softwaremill.session._
import org.json4s.JValue

import scala.concurrent.ExecutionContext

trait SessionComponents {

  implicit def system: ActorSystem
  implicit def executor: ExecutionContext

  def firestore: Firestore

  private implicit lazy val scheduler: Scheduler                                     = system.scheduler
  private[session] lazy val persistentUserSessionMapper: PersistentUserSessionMapper = wire[PersistentUserSessionMapper]
  private[session] lazy val refreshTokenDataMapper: RefreshTokenDataMapper           = wire[RefreshTokenDataMapper]
  private[session] lazy val documentSnapshotMapper: DocumentSnapshotMapper           = wire[DocumentSnapshotMapper]
  private[session] lazy val userSessionRepository: UserSessionRepository             = wire[FirestoreUserSessionRepository]
  private implicit lazy val refreshTokenStorage: RefreshTokenStorage[UserSession]    = wire[UserSessionRefreshTokenStorage]
  private implicit lazy val sessionConfig: SessionConfig                             = SessionConfig.fromConfig()
  private implicit lazy val serializer: SessionSerializer[UserSession, JValue]       = JValueSessionSerializer.caseClass[UserSession]
  private implicit lazy val encoder: SessionEncoder[UserSession]                     = new JwtSessionEncoder[UserSession]
  private implicit lazy val sessionManager: SessionManager[UserSession]              = new SessionManager[UserSession](sessionConfig)

  lazy val userSessionDirectives: UserSessionDirectives = wire[UserSessionDirectives]
}
