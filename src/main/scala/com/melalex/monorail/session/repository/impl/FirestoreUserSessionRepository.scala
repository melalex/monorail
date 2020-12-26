package com.melalex.monorail.session.repository.impl

import com.google.cloud.firestore.Firestore
import com.melalex.monorail.session.mapper.DocumentSnapshotMapper
import com.melalex.monorail.session.model.PersistentUserSession
import com.melalex.monorail.session.repository.UserSessionRepository
import com.melalex.monorail.session.repository.impl.FirestoreUserSessionRepository.CollectionName
import com.melalex.monorail.util.FirestoreUtil.{ApiFutureOps, DocumentReferenceOps}

import scala.concurrent.{ExecutionContext, Future}

object FirestoreUserSessionRepository {

  val CollectionName = "session"
}

private[session] class FirestoreUserSessionRepository(
    firestore: Firestore,
    documentSnapshotMapper: DocumentSnapshotMapper
)(implicit executionContext: ExecutionContext)
    extends UserSessionRepository {

  override def findBySelector(selector: String): Future[Option[PersistentUserSession]] =
    firestore
      .collection(CollectionName)
      .document(selector)
      .toFuture
      .map(_.map(documentSnapshotMapper.mapAToB))

  override def save(persistentUserSession: PersistentUserSession): Future[Unit] =
    firestore
      .collection(CollectionName)
      .document(persistentUserSession.selector)
      .set(persistentUserSession)
      .toFuture
      .map(_ => ())

  override def deleteBySelector(selector: String): Future[Unit] =
    firestore
      .collection(CollectionName)
      .document(selector)
      .delete()
      .toFuture
      .map(_ => ())
}
