package com.melalex.monorail.user.model

import java.net.URL
import java.util.Locale

case class User(
    id: String,
    email: Option[String],
    emailVerified: Boolean,
    name: Option[String],
    pictureUrl: Option[URL],
    locale: Locale,
    familyName: Option[String],
    givenName: Option[String],
)
