package com.melalex.monorail.error.route

import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.Route
import com.melalex.monorail.error.dto.ProblemDto
import com.melalex.monorail.error.model.Problem
import com.melalex.monorail.i18n.route.I18nDirectives
import com.melalex.monorail.i18n.service.I18nService
import io.scalaland.chimney.dsl.TransformerOps

class ProblemDirectives(i18nService: I18nService, i18nDirectives: I18nDirectives) {

  import i18nDirectives._
  import i18nService._

  def error(problem: Problem)(implicit toEntityMarshaller: ToEntityMarshaller[ProblemDto]): Route = locale { implicit locale =>
    val status = problem.status
      .map(StatusCode.int2StatusCode)
      .getOrElse(StatusCodes.InternalServerError)

    val dto = problem
      .into[ProblemDto]
      .withFieldComputed(_.tittle, _.tittleKey.map(getMessage))
      .withFieldComputed(_.detail, _.detailKey.map(getMessage))
      .transform

    complete(status, dto)
  }
}
