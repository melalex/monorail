package com.melalex.monorail.i18n

import com.melalex.monorail.i18n.service.I18nService
import com.melalex.monorail.i18n.service.impl.ResourceBundleI18nService
import com.softwaremill.macwire.wire

trait I18nComponents {

  lazy val i18nService: I18nService = wire[ResourceBundleI18nService]
}
