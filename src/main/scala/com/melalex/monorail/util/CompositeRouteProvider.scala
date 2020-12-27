package com.melalex.monorail.util

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.melalex.monorail.error._
import com.melalex.monorail.error.model.ThrowableProblem
import com.melalex.monorail.error.route.ProblemDirectives
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

class CompositeRouteProvider(
    delegates: Set[RouteProvider],
    problemDirectives: ProblemDirectives
) extends FailFastCirceSupport {

  import problemDirectives._

  def provideRoute: Route =
    handleExceptions(myExceptionHandler) {
      delegates
        .map(_.provideRoute)
        .reduce((r1, r2) => r1 ~ r2)
    }

  private val myExceptionHandler: ExceptionHandler = ExceptionHandler {
    case ThrowableProblem(problem, _) => error(problem)
    case _                            => error(internalServerError)
  }
}
