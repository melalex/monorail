package com.melalex.monorail.health

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.melalex.monorail.BaseRouteSpec
import com.melalex.monorail.config.AppComponents
import com.sun.tools.javac.jvm.Gen
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HealthComponentsTest extends BaseRouteSpec with AppComponents {

  "/health endpoint" should {

    "return OK response" in {
      Get("/api/v1/health") ~> routes ~> check {

      }
    }
  }
}
