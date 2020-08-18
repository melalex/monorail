package com.melalex.monorail

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

trait BaseUnitTestSpec extends AnyFlatSpec with Matchers with MockFactory {}
