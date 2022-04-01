package com.qa.scenario

import com.qa.BaseSimulation
import com.qa.models.Login
import io.gatling.core.Predef._

import scala.concurrent.duration._

case class LoginScenario() extends BaseSimulation {

  val successfulScn =
    scenario(getClass.getSimpleName)
      .exec(Login.loginSuccessful)

  val unsuccessfulScn =
    scenario(getClass.getSimpleName)
      .exec(Login.loginUnsuccessful)

  setUp(
    successfulScn.inject(constantUsersPerSec(1) during(3.minutes))
  .protocols(httpProtocol).throttle(reachRps(20) in (30.seconds)),
    unsuccessfulScn.inject(atOnceUsers(3),
      nothingFor(5.seconds),
      rampUsers(10) during(20.seconds),
      constantUsersPerSec(1) during(20.seconds))
  .protocols(httpProtocol))
}
