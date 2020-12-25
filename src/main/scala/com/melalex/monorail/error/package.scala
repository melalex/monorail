package com.melalex.monorail

import com.melalex.monorail.error.model.Problem

package object error {

  def invalidCredentials: Problem = ???

  def invalidAuthProvider: Problem = ???

  def badGateway: Problem = ???

  def internalServerError: Problem = Problem(
    internalMessage = "Unexpected error",
    problemType = Some("")
  )
}
