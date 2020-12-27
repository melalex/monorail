package com.melalex.monorail.user.dto

import java.net.URL
import java.util.Locale

case class UserDto(
    id: String,
    email: Option[String],
    emailVerified: Boolean,
    name: Option[String],
    pictureUrl: Option[URL],
    locale: Option[Locale],
    familyName: Option[String],
    givenName: Option[String],
)
