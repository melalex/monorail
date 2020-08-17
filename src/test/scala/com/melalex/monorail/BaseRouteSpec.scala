package com.melalex.monorail

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

trait BaseRouteSpec extends AnyFlatSpec with Matchers with ScalatestRouteTest {}
