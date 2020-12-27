package com.melalex.monorail.config.property

import scala.reflect.io.Path

case class GoogleAuthProperties(
    endpoint: String,
    redirectUrl: String,
    clientSecret: String
) {

  def clientSecretPath: Path = Path(clientSecret)
}
