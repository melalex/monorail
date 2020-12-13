package com.melalex.monorail

import scala.reflect.ClassTag

package object util {

  def cast[T: ClassTag](o: Any): Option[T] = o match {
    case v: T => Some(v)
    case _    => None
  }
}
