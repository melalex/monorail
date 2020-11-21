package com.melalex.monorail.user.model

import java.net.URL
import java.util.Locale

case class User(
    id: String,
    email: String,
    emailVerified: Boolean,
    name: String,
    pictureUrl: URL,
    locale: Locale,
    familyName: String,
    givenName: String
)
