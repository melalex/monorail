package com.melalex.monorail.session.transformer

import java.util.Locale

import com.google.cloud.firestore.DocumentSnapshot
import com.melalex.monorail.session.model.{PersistentUserSession, UserSession}
import com.softwaremill.session.{RefreshTokenData, RefreshTokenLookupResult}
import io.scalaland.chimney.Transformer

trait SessionTransformations {

  implicit val documentSnapshotTransformer: Transformer[DocumentSnapshot, PersistentUserSession] = source =>
    PersistentUserSession(
      userId = source.get("userId", classOf[String]),
      locale = source.get("userId", classOf[String]),
      selector = source.get("selector", classOf[String]),
      tokenHash = source.get("tokenHash", classOf[String]),
      expires = source.get("expires", classOf[Long])
  )

  implicit val persistentUserSessionTransformer: Transformer[PersistentUserSession, RefreshTokenLookupResult[UserSession]] = source =>
    RefreshTokenLookupResult(
      tokenHash = source.tokenHash,
      expires = source.expires,
      createSession = () => UserSession(userId = source.userId, locale = new Locale(source.locale))
  )

  implicit val refreshTokenDataTransformer: Transformer[RefreshTokenData[UserSession], PersistentUserSession] = source =>
    PersistentUserSession(
      userId = source.forSession.userId,
      locale = source.forSession.locale.getLanguage,
      selector = source.selector,
      tokenHash = source.tokenHash,
      expires = source.expires
  )
}
