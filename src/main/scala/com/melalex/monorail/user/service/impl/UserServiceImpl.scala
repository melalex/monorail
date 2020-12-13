package com.melalex.monorail.user.service.impl

import com.melalex.monorail.user.model.User
import com.melalex.monorail.user.repository.UserRepository
import com.melalex.monorail.user.service.UserService

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl(userRepository: UserRepository)(implicit executionContext: ExecutionContext) extends UserService {

  override def saveUser(user: User): Future[User] =
    userRepository
      .save(user)
      .map(_ => user)
}
