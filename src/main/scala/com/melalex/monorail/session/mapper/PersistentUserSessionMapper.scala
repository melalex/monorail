package com.melalex.monorail.session.mapper

import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.melalex.monorail.util.CustomMapper
import com.softwaremill.session.RefreshTokenLookupResult

class PersistentUserSessionMapper extends CustomMapper[PersistentUserSession, RefreshTokenLookupResult[UserSession]] {

  override def mapAToB(source: PersistentUserSession): RefreshTokenLookupResult[UserSession] = RefreshTokenLookupResult(
    tokenHash = source.tokenHash,
    expires = source.expires,
    createSession = () => UserSession(userId = source.userId, locale = source.locale)
  )
}
