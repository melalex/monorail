package com.melalex.monorail.config.property

import scala.reflect.io.Path

case class GoogleProperties(
    auth: GoogleAuthProperties,
    projectId: String,
    credentials: String
) {

  def credentialsPath: Path = Path(credentials)
}
