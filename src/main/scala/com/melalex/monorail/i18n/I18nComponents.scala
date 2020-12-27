package com.melalex.monorail.i18n

import com.melalex.monorail.config.property.MonorailProperties
import com.melalex.monorail.i18n.route.I18nDirectives
import com.melalex.monorail.i18n.service.I18nService
import com.melalex.monorail.i18n.service.impl.ResourceBundleI18nService
import com.softwaremill.macwire.wire

trait I18nComponents {

  def monorailProperties: MonorailProperties

  lazy val i18nService: I18nService       = wire[ResourceBundleI18nService]
  lazy val i18nDirectives: I18nDirectives = wire[I18nDirectives]
}
