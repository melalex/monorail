package com.melalex.monorail.config

import java.io.FileReader

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.json.jackson2.JacksonFactory
import com.melalex.monorail.config.property.GoogleProperties
import pureconfig.{ConfigObjectSource, ConfigReader, ConfigWriter, Exported}

trait GoogleComponents {

  def config: ConfigObjectSource
  def exportReader[A]: Exported[ConfigReader[A]]
  def exportWriter[A]: Exported[ConfigWriter[A]]

  // Properties
  lazy val googleProperties: GoogleProperties = config
    .at("google")
    .loadOrThrow[GoogleProperties]

  lazy val googleClientSecrets: GoogleClientSecrets = GoogleClientSecrets
    .load(JacksonFactory.getDefaultInstance, new FileReader(googleProperties.secret.jfile))
}
