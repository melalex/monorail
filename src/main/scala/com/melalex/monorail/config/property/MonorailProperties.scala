package com.melalex.monorail.config.property

case class MonorailProperties(
    server: ServerProperties,
    supportedLocales: List[String]
)
