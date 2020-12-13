package com.melalex.monorail.auth.service.google.impl

import com.google.api.client.auth.oauth2.TokenResponseException
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeTokenRequest, GoogleClientSecrets, GoogleTokenResponse}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.melalex.monorail.auth.service.google.{GoogleAuthResponse, GoogleClient}
import com.melalex.monorail.config.properties.GoogleAuthProperties
import com.melalex.monorail.error.model.ThrowableProblem
import com.melalex.monorail.error.{badGateway, internalServerError, invalidCredentials}
import com.melalex.monorail.util.cast

import java.io.IOException
import java.net.URL
import java.util.Locale
import scala.concurrent.{ExecutionContext, Future}

class GoogleApiGoogleClient(
    clientSecrets: GoogleClientSecrets,
    googleAuthProperties: GoogleAuthProperties
)(implicit executionContext: ExecutionContext)
    extends GoogleClient {

  override def exchangeCode(code: String): Future[GoogleAuthResponse] =
    Future
      .successful(createAuthRequest(code))
      .map(_.execute)
      .map(toGoogleAuthResponse)
      .recover {
        case ex: IOException            => throw ThrowableProblem(badGateway(), ex)
        case ex: TokenResponseException => throw ThrowableProblem(invalidCredentials(), ex)
        case ex                         => throw ThrowableProblem(internalServerError(), ex)
      }

  private def createAuthRequest(code: String) = {
    new GoogleAuthorizationCodeTokenRequest(
      new NetHttpTransport,
      JacksonFactory.getDefaultInstance,
      googleAuthProperties.endpoint,
      clientSecrets.getDetails.getClientId,
      clientSecrets.getDetails.getClientSecret,
      code,
      googleAuthProperties.redirectUrl
    )
  }

  private def toGoogleAuthResponse(tokenResponse: GoogleTokenResponse) = {
    val idToken = tokenResponse.parseIdToken
    val payload = idToken.getPayload

    GoogleAuthResponse(
      id = payload.getSubject,
      email = Option(payload.getEmail),
      emailVerified = payload.getEmailVerified != null && payload.getEmailVerified,
      name = Option(payload.get("name")).flatMap(cast[String]),
      pictureUrl = Option(payload.get("picture")).flatMap(cast[String]).map(new URL(_)),
      locale = Option(payload.get("locale")).flatMap(cast[String]).map(new Locale(_)),
      familyName = Option(payload.get("family_name")).flatMap(cast[String]),
      givenName = Option(payload.get("given_name")).flatMap(cast[String]),
      accessToken = tokenResponse.getAccessToken
    )
  }
}
