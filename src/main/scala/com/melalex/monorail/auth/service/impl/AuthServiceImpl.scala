package com.melalex.monorail.auth.service.impl

import com.melalex.monorail.auth.model.AuthProvider.AuthProvider
import com.melalex.monorail.auth.service.AuthService
import com.melalex.monorail.auth.service.verifier.TokenVerifier
import com.melalex.monorail.user.model.User
import com.melalex.monorail.user.service.UserService
import io.scalaland.chimney.dsl.TransformerOps

import scala.concurrent.{ExecutionContext, Future}

class AuthServiceImpl(verifiers: Map[AuthProvider, TokenVerifier], userService: UserService)(implicit executionContext: ExecutionContext)
    extends AuthService {

  override def authenticate(authProvider: AuthProvider, code: String): Future[User] =
    verifiers(authProvider)
      .verify(code)
      .map(_.transformInto[User])
      .flatMap(userService.saveUser)
}
