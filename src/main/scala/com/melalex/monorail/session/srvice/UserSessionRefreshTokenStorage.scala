package com.melalex.monorail.session.srvice

import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.melalex.monorail.session.repository.UserSessionRepository
import com.melalex.monorail.util.CustomMapper
import com.softwaremill.session.{RefreshTokenData, RefreshTokenLookupResult, RefreshTokenStorage}
import org.slf4j.LoggerFactory

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}

class UserSessionRefreshTokenStorage(
    private val userSessionRepository: UserSessionRepository,
    private val refreshTokenDataMapper: CustomMapper[RefreshTokenData[UserSession], PersistentUserSession],
    private val persistentUserSessionMapper: CustomMapper[PersistentUserSession, RefreshTokenLookupResult[UserSession]]
)(implicit executor: ExecutionContext)
    extends RefreshTokenStorage[UserSession] {

  private val log = LoggerFactory.getLogger(classOf[UserSessionRefreshTokenStorage])

  override def lookup(selector: String): Future[Option[RefreshTokenLookupResult[UserSession]]] =
    userSessionRepository
      .findBySelector(selector)
      .map(opt => opt.map(persistentUserSessionMapper.mapAToB))

  override def store(data: RefreshTokenData[UserSession]): Future[Unit] = userSessionRepository.save(refreshTokenDataMapper.mapAToB(data))

  override def remove(selector: String): Future[Unit] = userSessionRepository.deleteBySelector(selector)

  override def schedule[S](after: Duration)(op: => Future[S]): Unit = {
    log.debug("Running scheduled operation immediately")
    op
    Future.successful(())
  }
}
