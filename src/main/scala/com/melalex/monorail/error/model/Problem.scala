package com.melalex.monorail.error.model

case class Problem(
    internalMessage: String,
    typeKey: Option[String] = None,
    tittleKey: Option[String] = None,
    status: Option[Int] = None,
    detailKey: Option[String] = None,
    instanceKey: Option[String] = None,
    causes: Seq[Problem] = Nil
)
