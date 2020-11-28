package com.melalex.monorail

import com.melalex.monorail.error.model.Problem

package object error {

  def invalidCredentials(): Problem = ???

  def internalServerError(): Problem = ???
}
