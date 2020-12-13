package com.melalex.monorail.config.properties

case class MonorailProperties(
    server: ServerProperties,
    supportedLocales: List[String]
)
