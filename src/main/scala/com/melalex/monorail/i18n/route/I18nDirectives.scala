package com.melalex.monorail.i18n.route

import java.util.Locale

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives.selectPreferredLanguage
import com.melalex.monorail.config.property.MonorailProperties

class I18nDirectives(monorailProperties: MonorailProperties) {

  val locale: Directive1[Locale] =
    selectPreferredLanguage(monorailProperties.supportedLocales.head, monorailProperties.supportedLocales.tail)
      .map(lang => Locale.forLanguageTag(lang.primaryTag))
}
