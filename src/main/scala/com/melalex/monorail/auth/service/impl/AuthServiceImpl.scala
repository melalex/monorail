package com.melalex.monorail.auth.service.impl

import com.melalex.monorail.auth.service.AuthService
import com.melalex.monorail.auth.service.google.GoogleClient
import com.melalex.monorail.user.model.User
import com.melalex.monorail.user.service.UserService
import io.scalaland.chimney.dsl.TransformerOps

import scala.concurrent.{ExecutionContext, Future}

class AuthServiceImpl(googleClient: GoogleClient, userService: UserService)(implicit executionContext: ExecutionContext)
    extends AuthService {

  override def authWithGoogle(code: String): Future[User] =
    googleClient
      .exchangeCode(code)
      .map(_.transformInto[User])
      .flatMap(userService.saveUser)
}
