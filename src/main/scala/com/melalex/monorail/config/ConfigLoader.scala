package com.melalex.monorail.config

import com.typesafe.config.{Config, ConfigFactory}

object ConfigLoader {

  def load: Config = ConfigFactory.load("application.conf")
}
