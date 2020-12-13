package com.melalex.monorail.i18n.service

import java.util.Locale

trait I18nService {

  def getMessage(key: String)(implicit locale: Locale): String
}
