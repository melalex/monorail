package com.melalex.monorail.error

import com.melalex.monorail.error.route.ProblemDirectives
import com.melalex.monorail.i18n.route.I18nDirectives
import com.melalex.monorail.i18n.service.I18nService
import com.softwaremill.macwire.wire

trait ErrorComponents {

  def i18nService: I18nService
  def i18nDirectives: I18nDirectives

  val problemDirectives: ProblemDirectives = wire[ProblemDirectives]
}
