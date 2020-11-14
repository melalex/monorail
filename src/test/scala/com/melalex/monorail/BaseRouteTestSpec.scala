package com.melalex.monorail

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.melalex.monorail.config.AppComponents
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

trait BaseRouteTestSpec extends AnyFlatSpec with Matchers with ScalatestRouteTest with AppComponents with FailFastCirceSupport
