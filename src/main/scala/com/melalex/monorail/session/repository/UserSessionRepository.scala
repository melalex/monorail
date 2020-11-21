package com.melalex.monorail.session.repository

import com.melalex.monorail.session.model.PersistentUserSession

import scala.concurrent.Future

trait UserSessionRepository {

  def findBySelector(selector: String): Future[Option[PersistentUserSession]]

  def save(persistentUserSession: PersistentUserSession): Future[Unit]

  def deleteBySelector(selector: String): Future[Unit]
}
