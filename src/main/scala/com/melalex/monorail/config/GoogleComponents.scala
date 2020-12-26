package com.melalex.monorail.config

import java.io.{FileInputStream, FileReader}

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.{Firestore, FirestoreOptions}
import com.melalex.monorail.config.property.GoogleProperties
import pureconfig.{ConfigObjectSource, ConfigReader, ConfigWriter, Exported}

import scala.util.Using

trait GoogleComponents {

  def config: ConfigObjectSource
  def exportReader[A]: Exported[ConfigReader[A]]
  def exportWriter[A]: Exported[ConfigWriter[A]]

  // Firestore
  lazy val firestore: Firestore = FirestoreOptions.getDefaultInstance.toBuilder
    .setProjectId(googleProperties.projectId)
    .setCredentials(googleCredentials)
    .build
    .getService

  // Properties
  lazy val googleProperties: GoogleProperties = config
    .at("google")
    .loadOrThrow[GoogleProperties]

  lazy val googleClientSecrets: GoogleClientSecrets = GoogleClientSecrets
    .load(JacksonFactory.getDefaultInstance, new FileReader(googleProperties.auth.clientSecret.jfile))

  lazy val googleCredentials: GoogleCredentials = Using(new FileInputStream(googleProperties.credentials.jfile)) { input =>
    GoogleCredentials.fromStream(input)
  }.get
}
