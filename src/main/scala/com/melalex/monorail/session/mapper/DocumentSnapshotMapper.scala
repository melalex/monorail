package com.melalex.monorail.session.mapper

import com.google.cloud.firestore.DocumentSnapshot
import com.melalex.monorail.session.model.PersistentUserSession
import com.melalex.monorail.util.CustomMapper

class DocumentSnapshotMapper extends CustomMapper[DocumentSnapshot, PersistentUserSession] {

  override def mapAToB(source: DocumentSnapshot): PersistentUserSession = PersistentUserSession(
    userId = source.get("userId", classOf[String]),
    locale = source.get("userId", classOf[String]),
    selector = source.get("selector", classOf[String]),
    tokenHash = source.get("tokenHash", classOf[String]),
    expires = source.get("expires", classOf[Long])
  )
}
