package com.melalex.monorail

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

trait BaseRouteSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {}
