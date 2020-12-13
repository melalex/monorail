package com.melalex.monorail.user.repository

import com.melalex.monorail.user.model.User

import scala.concurrent.Future

trait UserRepository {

  def save(user: User): Future[Unit]
}
