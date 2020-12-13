package com.melalex.monorail.util

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.{DocumentReference, DocumentSnapshot, FirestoreException}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Try

object FirestoreUtil {

  implicit class DocumentReferenceOps(documentReference: DocumentReference) {

    def toFuture: Future[Option[DocumentSnapshot]] = {
      val promise = Promise[Option[DocumentSnapshot]]()

      documentReference.addSnapshotListener((value: DocumentSnapshot, error: FirestoreException) => {
        if (error != null) {
          promise.tryFailure(error)
        } else if (value.getData == null || !value.exists()) {
          promise.success(None)
        } else {
          promise.success(Some(value))
        }
      })

      promise.future
    }
  }

  implicit class ApiFutureOps[T](future: ApiFuture[T]) {

    def toFuture(implicit executionContext: ExecutionContext): Future[T] = {
      val promise = Promise[T]()

      future.addListener(() => promise.complete(Try(future.get)), executionContext)

      promise.future
    }
  }
}
