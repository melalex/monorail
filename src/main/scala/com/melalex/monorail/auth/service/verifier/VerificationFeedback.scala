package com.melalex.monorail.auth.service.verifier

import java.net.URL
import java.util.Locale

case class VerificationFeedback(
    id: String,
    email: Option[String],
    emailVerified: Boolean,
    name: Option[String],
    pictureUrl: Option[URL],
    locale: Option[Locale],
    familyName: Option[String],
    givenName: Option[String],
    accessToken: String
)
