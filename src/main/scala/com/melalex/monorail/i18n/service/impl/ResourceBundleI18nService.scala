package com.melalex.monorail.i18n.service.impl

import java.util.{Locale, ResourceBundle}

import com.melalex.monorail.i18n.service.I18nService

class ResourceBundleI18nService extends I18nService {

  override def getMessage(key: String)(implicit locale: Locale): String = ResourceBundle
    .getBundle("i18n/messages", locale)
    .getString(key)
}
