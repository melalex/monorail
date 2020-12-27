package com.melalex.monorail.error.dto

import com.melalex.monorail.error.model.Problem

case class ProblemDto(
    problemType: Option[String] = None,
    tittle: Option[String] = None,
    status: Option[Int] = None,
    detail: Option[String] = None,
    instance: Option[String] = None,
    causes: Seq[Problem] = Nil
)
