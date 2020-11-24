package com.melalex.monorail.auth.service

import akka.stream.scaladsl.Source
import com.melalex.monorail.session.model.UserSession

import scala.concurrent.Future

trait AuthService {

  def authWithGoogle(code: String): Source[UserSession, Future[UserSession]]
}
