package com.melalex.monorail.session.model

private[session] case class PersistentUserSession(
    userId: String,
    locale: String,
    selector: String,
    tokenHash: String,
    expires: Long
)
