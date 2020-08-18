package com.melalex.monorail.health.services.impl

import akka.dispatch.Futures
import com.melalex.monorail.BaseUnitTestSpec
import com.melalex.monorail.health.models.{HealthCheckResult, SubSystemHealth}
import com.melalex.monorail.health.services.sensors.Sensor
import com.softwaremill.macwire.{wire, wireSet}
import org.scalatest.concurrent.ScalaFutures.whenReady

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class SensorBackedHealthServiceTest extends BaseUnitTestSpec {

  val sensor1: Sensor      = mock[Sensor]
  val sensor2: Sensor      = mock[Sensor]
  val sensor3: Sensor      = mock[Sensor]
  val sensors: Set[Sensor] = wireSet[Sensor]

  val executor: ExecutionContextExecutor = ExecutionContext.global

  val sensorBackedHealthService: SensorBackedHealthService = wire[SensorBackedHealthService]

  "SensorBackedHealthService" should "return expected response" in {
    val health1 = SubSystemHealth.ok("sensor1")
    val health2 = SubSystemHealth.ko("sensor2", "why not?")
    val health3 = SubSystemHealth.ok("sensor3")

    (() => sensor1.checkHealth).expects().returning(Futures.successful(health1))
    (() => sensor2.checkHealth).expects().returning(Futures.successful(health2))
    (() => sensor3.checkHealth).expects().returning(Futures.successful(health3))

    whenReady(sensorBackedHealthService.checkHealth) { result =>
      result shouldEqual HealthCheckResult(Set(health1, health2, health3))
    }
  }
}
