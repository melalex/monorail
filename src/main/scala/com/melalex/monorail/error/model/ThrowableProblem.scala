package com.melalex.monorail.error.model

case class ThrowableProblem(problem: Problem, cause: Throwable = null) extends Throwable(problem.internalMessage, cause)
