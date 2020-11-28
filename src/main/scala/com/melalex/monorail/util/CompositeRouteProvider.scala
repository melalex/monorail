package com.melalex.monorail.util

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.melalex.monorail.error._
import com.melalex.monorail.error.dto.ProblemDto
import com.melalex.monorail.error.model.{Problem, ThrowableProblem}
import com.melalex.monorail.i18n.I18nDirectives._
import com.melalex.monorail.i18n.service.I18nService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.scalaland.chimney.dsl.TransformerOps

class CompositeRouteProvider(delegates: Set[RouteProvider], i18nService: I18nService) extends RouteProvider with FailFastCirceSupport {

  def provideRoute: Route =
    handleExceptions(myExceptionHandler) {
      delegates
        .map(_.provideRoute)
        .reduce((r1, r2) => r1 ~ r2)
    }

  private val myExceptionHandler: ExceptionHandler = ExceptionHandler {
    case ThrowableProblem(problem, _) => completeWithProblem(problem)
    case _                            => completeWithProblem(internalServerError())
  }

  private def completeWithProblem(problem: Problem): Route = locale { locale =>
    val status = problem.status
      .map(StatusCode.int2StatusCode)
      .getOrElse(StatusCodes.InternalServerError)

    val dto = problem
      .into[ProblemDto]
      .withFieldComputed(_.tittle, _.tittleKey.map(i18nService.getLocalizedStringByCode(_, locale)))
      .withFieldComputed(_.detail, _.detailKey.map(i18nService.getLocalizedStringByCode(_, locale)))
      .transform

    complete(status, dto)
  }
}
