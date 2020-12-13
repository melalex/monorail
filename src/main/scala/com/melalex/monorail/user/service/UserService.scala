package com.melalex.monorail.user.service

import com.melalex.monorail.user.model.User

import scala.concurrent.Future

trait UserService {

  def saveUser(user: User): Future[User]
}
