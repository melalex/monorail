package com.melalex.monorail.support

trait CustomMapper[A, B] {

  def map(source: A): B

  def reverseMap(source: B): A = throw new UnsupportedOperationException("Not implemented")
}
