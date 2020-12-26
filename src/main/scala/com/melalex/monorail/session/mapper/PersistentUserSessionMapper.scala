package com.melalex.monorail.session.mapper

import java.util.Locale

import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.melalex.monorail.util.CustomMapper
import com.softwaremill.session.RefreshTokenLookupResult

private[session] class PersistentUserSessionMapper extends CustomMapper[PersistentUserSession, RefreshTokenLookupResult[UserSession]] {

  override def mapAToB(source: PersistentUserSession): RefreshTokenLookupResult[UserSession] = RefreshTokenLookupResult(
    tokenHash = source.tokenHash,
    expires = source.expires,
    createSession = () => UserSession(userId = source.userId, locale = new Locale(source.locale))
  )
}
