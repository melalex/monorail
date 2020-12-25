package com.melalex.monorail.auth.service.verifier

import scala.concurrent.Future

trait TokenVerifier {

  def verify(code: String): Future[VerificationFeedback]
}
