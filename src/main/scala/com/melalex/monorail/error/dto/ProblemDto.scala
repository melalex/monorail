package com.melalex.monorail.error.dto

import com.melalex.monorail.error.model.Problem
import io.circe.generic.JsonCodec
import io.circe.generic.extras._

@JsonCodec
case class ProblemDto(
    @JsonKey("type") problemType: Option[String] = None,
    tittle: Option[String] = None,
    status: Option[Int] = None,
    detail: Option[String] = None,
    instance: Option[String] = None,
    causes: Seq[Problem] = Nil
)
