package com.melalex.monorail.user.repository.impl

import com.google.cloud.firestore.Firestore
import com.melalex.monorail.user.model.User
import com.melalex.monorail.user.repository.UserRepository
import com.melalex.monorail.util.FirestoreUtil._

import scala.concurrent.{ExecutionContext, Future}

object FirestoreUserRepository {

  val CollectionName = "users"
}

class FirestoreUserRepository(firestore: Firestore)(implicit executionContext: ExecutionContext) extends UserRepository {

  override def save(user: User): Future[Unit] =
    firestore
      .collection(FirestoreUserRepository.CollectionName)
      .document(user.id)
      .set(user)
      .toFuture
      .map(_ => ())
}
