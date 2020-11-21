package com.melalex.monorail.session.repository.impl

import com.melalex.monorail.session.model.PersistentUserSession
import com.melalex.monorail.session.repository.UserSessionRepository

import scala.concurrent.Future

class FirestoreUserSessionRepository extends UserSessionRepository {

  override def findBySelector(selector: String): Future[Option[PersistentUserSession]] = Future.successful(None)

  override def save(persistentUserSession: PersistentUserSession): Future[Unit] = Future.successful()

  override def deleteBySelector(selector: String): Future[Unit] = Future.successful()
}
