package com.qa.scenario

import com.qa.BaseSimulation
import com.qa.models.HomePage
import io.gatling.core.Predef._

case class VisitScenario() extends BaseSimulation {

  val scn =
    scenario(getClass.getSimpleName)
      exec(initSession)
      .exec(HomePage.getHomePage).exitHereIfFailed
      .pause(3)
      .exec(HomePage.getAboutUs).exitHereIfFailed

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))

}
