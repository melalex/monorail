package com.melalex.monorail.error.dto

import com.melalex.monorail.error.model.Problem
import io.circe.generic.JsonCodec
import io.circe.generic.extras._

@JsonCodec
case class ProblemDto(
    internalMessage: String,
    @JsonKey("type") problemType: Option[String],
    tittle: Option[String],
    status: Option[Int],
    detail: Option[String],
    instance: Option[String],
    causes: Seq[Problem]
)
