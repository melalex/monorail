package com.melalex.monorail.auth.model

case class User(
    id: String,
    email: String,
    emailVerified: Boolean,
    name: String,
    pictureUrl: String,
    locale: String,
    familyName: String,
    givenName: String
)
