package com.melalex.monorail.session.model

import java.util.Locale

private[session] case class PersistentUserSession(
    userId: String,
    locale: Locale,
    selector: String,
    tokenHash: String,
    expires: Long
)
