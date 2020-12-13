package com.melalex.monorail.auth.service.google

import scala.concurrent.Future

trait GoogleClient {

  def exchangeCode(code: String): Future[GoogleAuthResponse]
}
