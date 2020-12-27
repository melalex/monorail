package com.melalex.monorail.user.transformer

import com.melalex.monorail.session.model.UserSession
import com.melalex.monorail.user.model.User
import io.scalaland.chimney.Transformer

trait UserTransformations {

  implicit val userTransformer: Transformer[User, UserSession] = source =>
    UserSession(
      userId = source.id,
      locale = source.locale
  )
}
