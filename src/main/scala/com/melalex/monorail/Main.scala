package com.melalex.monorail

import com.melalex.monorail.config.{AkkaComponents, AppComponents}
import com.melalex.monorail.server.Server
import com.softwaremill.macwire.wire

object Main extends App with AppComponents with AkkaComponents {
  wire[Server].run()
}
