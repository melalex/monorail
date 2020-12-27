package com.melalex.monorail.user

import akka.actor.ActorSystem
import com.google.cloud.firestore.Firestore
import com.melalex.monorail.user.repository.impl.FirestoreUserRepository
import com.melalex.monorail.user.service.impl.UserServiceImpl
import com.softwaremill.macwire.wire

import scala.concurrent.ExecutionContext

trait UserComponents {

  implicit def system: ActorSystem
  implicit def executor: ExecutionContext

  def firestore: Firestore

  // Services
  lazy val userService: UserServiceImpl = wire[UserServiceImpl]

  // Repositories
  private[user] lazy val userRepository = wire[FirestoreUserRepository]
}
