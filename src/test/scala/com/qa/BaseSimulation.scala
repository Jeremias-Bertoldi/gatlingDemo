package com.qa

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BaseSimulation extends Simulation {

  val csvFeederLoginSuccessful = csv("data/loginSuccessful.csv").circular
  val csvFeederLoginWithFails = csv("data/loginFailed.csv").circular

  val domain = "demostore.gatling.io"

  val httpProtocol = http
    .baseUrl("http://" + domain )
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
}
