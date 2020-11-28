package com.melalex.monorail.i18n

import java.util.Locale

import akka.http.scaladsl.server.Directive
import akka.http.scaladsl.server.Directives.selectPreferredLanguage

object I18nDirectives {

  val locale: Directive[Tuple1[Locale]] = selectPreferredLanguage("*")
    .map(lang => Locale.forLanguageTag(lang.primaryTag))
}
