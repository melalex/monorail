package com.melalex.monorail.config

import com.melalex.monorail.config.property.{GoogleProperties, MonorailProperties}
import pureconfig.generic.ExportMacros
import pureconfig._

trait PureConfigComponents {

  implicit val config: ConfigObjectSource = ConfigSource.default

  implicit def exportReader[A]: Exported[ConfigReader[A]] =
    macro ExportMacros.exportDerivedReader[A]

  implicit def exportWriter[A]: Exported[ConfigWriter[A]] =
    macro ExportMacros.exportDerivedWriter[A]

  lazy val monorailProperties: MonorailProperties = config
    .at("monorail")
    .loadOrThrow[MonorailProperties]

  lazy val googleProperties: GoogleProperties = config
    .at("google")
    .loadOrThrow[GoogleProperties]
}
