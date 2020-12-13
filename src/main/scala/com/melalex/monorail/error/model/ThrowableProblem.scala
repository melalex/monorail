package com.melalex.monorail.error.model

case class ThrowableProblem(problem: Problem, cause: Throwable = null) extends Throwable(problem.internalMessage, cause) {

  def this(
      internalMessage: String,
      problemType: Option[String] = None,
      tittleKey: Option[String] = None,
      status: Option[Int] = None,
      detailKey: Option[String] = None,
      instance: Option[String] = None,
      causes: Seq[Problem] = Nil,
      cause: Throwable = null
  ) = this(Problem(internalMessage, problemType, tittleKey, status, detailKey, instance, causes), cause)
}
