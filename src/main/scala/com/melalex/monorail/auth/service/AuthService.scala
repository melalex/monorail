package com.melalex.monorail.auth.service

import com.melalex.monorail.user.model.User

import scala.concurrent.Future
import scala.util.Try

trait AuthService {

  def authWithGoogle(code: String): Future[User]
}
