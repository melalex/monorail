package com.melalex.monorail.session.srvice

import akka.actor.Scheduler
import akka.pattern.after
import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.melalex.monorail.session.repository.UserSessionRepository
import com.melalex.monorail.session.transformer.SessionTransformations
import com.softwaremill.session.{RefreshTokenData, RefreshTokenLookupResult, RefreshTokenStorage}
import io.scalaland.chimney.dsl.TransformerOps

import scala.concurrent.duration.Duration.Infinite
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.{ExecutionContext, Future}

private[session] class UserSessionRefreshTokenStorage(
    userSessionRepository: UserSessionRepository,
    scheduler: Scheduler
)(implicit executor: ExecutionContext)
    extends RefreshTokenStorage[UserSession]
    with SessionTransformations {

  override def lookup(selector: String): Future[Option[RefreshTokenLookupResult[UserSession]]] =
    userSessionRepository
      .findBySelector(selector)
      .map(_.map(_.transformInto[RefreshTokenLookupResult[UserSession]]))

  override def store(data: RefreshTokenData[UserSession]): Future[Unit] =
    userSessionRepository.save(data.transformInto[PersistentUserSession])

  override def remove(selector: String): Future[Unit] = userSessionRepository.deleteBySelector(selector)

  override def schedule[S](duration: Duration)(op: => Future[S]): Unit = duration match {
    case finiteDuration: FiniteDuration => val _ = after(finiteDuration, scheduler)(op)
    case _ : Infinite => ()
  }
}
