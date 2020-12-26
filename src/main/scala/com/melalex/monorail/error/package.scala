package com.melalex.monorail

import com.melalex.monorail.error.model.Problem

package object error {

  def invalidCredentials: Problem = Problem(
    internalMessage = "Invalid credentials",
    tittleKey = Some("error.invalidCredentials.tittle"),
    detailKey = Some("error.invalidCredentials.details"),
    status = Some(401)
  )

  def invalidAuthProvider: Problem = Problem(
    internalMessage = "Invalid authentication provider",
    tittleKey = Some("error.invalidAuthProvider.tittle"),
    detailKey = Some("error.invalidAuthProvider.details"),
    status = Some(400)
  )

  def badGateway(service: String): Problem = Problem(
    internalMessage = s"Downstream service [ $service ] is unavailable",
    tittleKey = Some("error.badGateway.tittle"),
    detailKey = Some("error.badGateway.details"),
    status = Some(500)
  )

  def internalServerError: Problem = Problem(
    internalMessage = "Unexpected error",
    tittleKey = Some("error.unexpected.tittle"),
    detailKey = Some("error.unexpected.details"),
    status = Some(500)
  )
}
