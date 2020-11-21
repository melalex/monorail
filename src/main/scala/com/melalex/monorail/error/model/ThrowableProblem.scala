package com.melalex.monorail.error.model

case class ThrowableProblem(problem: Problem, cause: Throwable = null) extends Throwable(problem.internalMessage, cause) {

  def this(
      internalMessage: String,
      typeKey: Option[String] = None,
      tittleKey: Option[String] = None,
      status: Option[Int] = None,
      detailKey: Option[String] = None,
      instanceKey: Option[String] = None,
      causes: Seq[Problem] = Nil,
      cause: Throwable = null
  ) = this(Problem(internalMessage, typeKey, tittleKey, status, detailKey, instanceKey, causes), cause)
}
