package com.melalex.monorail.util

trait CustomMapper[A, B] {

  def mapAToB(source: A): B

  def mapBToA(source: B): A = throw new UnsupportedOperationException(s"$getClass.mapBToA is not implemented!")
}
