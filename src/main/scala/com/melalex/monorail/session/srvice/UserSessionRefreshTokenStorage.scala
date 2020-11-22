package com.melalex.monorail.session.srvice

import akka.actor.Scheduler
import akka.pattern.after
import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.melalex.monorail.session.repository.UserSessionRepository
import com.melalex.monorail.util.CustomMapper
import com.softwaremill.session.{RefreshTokenData, RefreshTokenLookupResult, RefreshTokenStorage}

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.{ExecutionContext, Future}

private[session] class UserSessionRefreshTokenStorage(
    private val userSessionRepository: UserSessionRepository,
    private val refreshTokenDataMapper: CustomMapper[RefreshTokenData[UserSession], PersistentUserSession],
    private val persistentUserSessionMapper: CustomMapper[PersistentUserSession, RefreshTokenLookupResult[UserSession]]
)(private implicit val executor: ExecutionContext, private implicit val scheduler: Scheduler)
    extends RefreshTokenStorage[UserSession] {

  override def lookup(selector: String): Future[Option[RefreshTokenLookupResult[UserSession]]] =
    userSessionRepository
      .findBySelector(selector)
      .map(opt => opt.map(persistentUserSessionMapper.mapAToB))

  override def store(data: RefreshTokenData[UserSession]): Future[Unit] = userSessionRepository.save(refreshTokenDataMapper.mapAToB(data))

  override def remove(selector: String): Future[Unit] = userSessionRepository.deleteBySelector(selector)

  override def schedule[S](duration: Duration)(op: => Future[S]): Unit = duration match {
    case finiteDuration: FiniteDuration => after(finiteDuration, scheduler)(op)
  }
}
