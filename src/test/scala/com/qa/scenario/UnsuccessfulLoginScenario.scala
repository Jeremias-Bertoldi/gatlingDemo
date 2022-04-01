package com.qa.scenario

import com.qa.BaseSimulation
import com.qa.models.{HomePage, Login}
import io.gatling.core.Predef._

import scala.concurrent.duration._

case class UnsuccessfulLoginScenario() extends BaseSimulation {

  val unsuccessfulScn =
    scenario(getClass.getSimpleName)
      .exec(initSession)
      .exec(HomePage.getHomePage)
      .exec(Login.goToLoginPage)
      .exec(Login.loginUnsuccessful).exitHereIfFailed

  setUp(
    unsuccessfulScn.inject(
      rampUsers(5) during(20.seconds),
      constantUsersPerSec(10) during(20.seconds))
      .protocols(httpProtocol)
    )
}
