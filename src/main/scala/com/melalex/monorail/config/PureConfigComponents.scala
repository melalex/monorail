package com.melalex.monorail.config

import pureconfig._
import pureconfig.generic.ExportMacros

trait PureConfigComponents {

  implicit val config: ConfigObjectSource = ConfigSource.default

  implicit def exportReader[A]: Exported[ConfigReader[A]] =
    macro ExportMacros.exportDerivedReader[A]

  implicit def exportWriter[A]: Exported[ConfigWriter[A]] =
    macro ExportMacros.exportDerivedWriter[A]
}
