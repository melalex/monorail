package com.melalex.monorail.auth.service.verifier.impl

import java.io.IOException
import java.net.URL
import java.util.Locale

import com.google.api.client.auth.oauth2.TokenResponseException
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeTokenRequest, GoogleClientSecrets, GoogleTokenResponse}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.melalex.monorail.auth.service.verifier.{TokenVerifier, VerificationFeedback}
import com.melalex.monorail.config.property.{GoogleProperties, MonorailProperties}
import com.melalex.monorail.error.model.ThrowableProblem
import com.melalex.monorail.error.{badGateway, internalServerError, invalidCredentials}
import com.melalex.monorail.util.cast

import scala.concurrent.{ExecutionContext, Future}

class GoogleTokenVerifier(
    clientSecrets: GoogleClientSecrets,
    googleProperties: GoogleProperties,
    monorailProperties: MonorailProperties
)(implicit executionContext: ExecutionContext)
    extends TokenVerifier {

  override def verify(code: String): Future[VerificationFeedback] =
    Future
      .successful(createAuthRequest(code))
      .map(_.execute)
      .map(toVerificationFeedback)
      .recover {
        case ex: TokenResponseException => throw ThrowableProblem(invalidCredentials, ex)
        case ex: IOException            => throw ThrowableProblem(badGateway("Google SSO"), ex)
        case ex                         => throw ThrowableProblem(internalServerError, ex)
      }

  private def createAuthRequest(code: String) = new GoogleAuthorizationCodeTokenRequest(
    new NetHttpTransport,
    JacksonFactory.getDefaultInstance,
    googleProperties.auth.endpoint,
    clientSecrets.getDetails.getClientId,
    clientSecrets.getDetails.getClientSecret,
    code,
    googleProperties.auth.redirectUrl
  )

  private def toVerificationFeedback(tokenResponse: GoogleTokenResponse) = {
    val idToken = tokenResponse.parseIdToken
    val payload = idToken.getPayload

    VerificationFeedback(
      id = payload.getSubject,
      email = Option(payload.getEmail),
      emailVerified = payload.getEmailVerified != null && payload.getEmailVerified,
      name = Option(payload.get("name"))
        .flatMap(cast[String]),
      pictureUrl = Option(payload.get("picture"))
        .flatMap(cast[String])
        .map(new URL(_)),
      locale = Option(payload.get("locale"))
        .flatMap(cast[String])
        .map(new Locale(_))
        .getOrElse(new Locale(monorailProperties.supportedLocales.head)),
      familyName = Option(payload.get("family_name"))
        .flatMap(cast[String]),
      givenName = Option(payload.get("given_name"))
        .flatMap(cast[String]),
      accessToken = tokenResponse.getAccessToken
    )
  }
}
