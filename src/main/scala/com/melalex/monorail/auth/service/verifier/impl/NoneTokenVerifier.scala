package com.melalex.monorail.auth.service.verifier.impl

import com.melalex.monorail.auth.service.verifier.{TokenVerifier, VerificationFeedback}
import com.melalex.monorail.error.invalidAuthProvider
import com.melalex.monorail.error.model.ThrowableProblem

import scala.concurrent.Future

class NoneTokenVerifier extends TokenVerifier {

  override def verify(code: String): Future[VerificationFeedback] = Future.failed(ThrowableProblem(invalidAuthProvider))
}
