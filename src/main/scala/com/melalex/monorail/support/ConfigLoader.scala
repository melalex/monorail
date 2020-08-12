package com.melalex.monorail.support

import com.typesafe.config.{Config, ConfigFactory}

object ConfigLoader {

  def load: Config = ConfigFactory.load("application.conf")
}
