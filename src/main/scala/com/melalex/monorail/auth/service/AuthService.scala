package com.melalex.monorail.auth.service

import com.melalex.monorail.auth.model.AuthProvider.AuthProvider
import com.melalex.monorail.user.model.User

import scala.concurrent.Future

trait AuthService {

  def authenticate(authProvider: AuthProvider, code: String): Future[User]
}
