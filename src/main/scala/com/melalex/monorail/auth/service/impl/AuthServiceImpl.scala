package com.melalex.monorail.auth.service.impl

import akka.stream.scaladsl.Flow
import com.melalex.monorail.auth.service.AuthService
import com.melalex.monorail.session.model.UserSession

class AuthServiceImpl extends AuthService {

  override val authWithGoogle: Flow[String, UserSession, UserSession] = Flow.fromFunction()
}
