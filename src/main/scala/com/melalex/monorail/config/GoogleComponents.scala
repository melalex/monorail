package com.melalex.monorail.config

import java.io.{FileInputStream, FileReader}

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.{Firestore, FirestoreOptions}
import com.melalex.monorail.config.property.GoogleProperties

import scala.util.Using

trait GoogleComponents {

  def googleProperties: GoogleProperties

  lazy val firestore: Firestore = FirestoreOptions.getDefaultInstance.toBuilder
    .setProjectId(googleProperties.projectId)
    .setCredentials(googleCredentials)
    .build
    .getService

  lazy val googleClientSecrets: GoogleClientSecrets = GoogleClientSecrets
    .load(JacksonFactory.getDefaultInstance, new FileReader(googleProperties.auth.clientSecretPath.jfile))

  lazy val googleCredentials: GoogleCredentials = Using(new FileInputStream(googleProperties.credentialsPath.jfile)) { input =>
    GoogleCredentials.fromStream(input)
  }.get
}
