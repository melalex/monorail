package com.melalex.monorail.i18n.service

import java.util.Locale

trait I18nService {

  def getLocalizedStringByCode(key: String, locale: Locale): String
}
