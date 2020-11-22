package com.melalex.monorail.session.mapper

import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.melalex.monorail.util.CustomMapper
import com.softwaremill.session.RefreshTokenData

private[session] class RefreshTokenDataMapper extends CustomMapper[RefreshTokenData[UserSession], PersistentUserSession] {

  override def mapAToB(source: RefreshTokenData[UserSession]): PersistentUserSession = PersistentUserSession(
    userId = source.forSession.userId,
    locale = source.forSession.locale,
    selector = source.selector,
    tokenHash = source.tokenHash,
    expires = source.expires
  )

  override def mapBToA(source: PersistentUserSession): RefreshTokenData[UserSession] = RefreshTokenData(
    forSession = UserSession(source.userId, source.locale),
    selector = source.selector,
    tokenHash = source.tokenHash,
    expires = source.expires
  )
}
