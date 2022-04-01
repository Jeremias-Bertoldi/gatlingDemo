package com.qa.models

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Login {

  val csvFeederLoginSuccessful = csv("data/loginSuccessful.csv").circular
  val csvFeederLoginWithFails = csv("data/loginFailed.csv").circular

  lazy val goToLoginPage =
      exec(http("Load Login Page")
        .get("/login")
        .check(status.is(200))
        .check(substring("Username:")))

  lazy val loginSuccessful =
    feed(csvFeederLoginSuccessful)
      .exec(http("Successful Login")
        .post("/login")
        .formParam("_csrf", "#{crsfValue}")
        .formParam("username", "#{username}")
        .formParam("password", "#{password}")
        .check(status.is(200))
      )
      .exec(session => session.set("customerLoggedIn", true))

  lazy val loginUnsuccessful =
    feed(csvFeederLoginWithFails)
      .exec(http("Unsuccessful Login")
        .post("/login")
        .formParam("_csrf", "#{crsfValue}")
        .formParam("username", "#{username}")
        .formParam("password", "#{password}")
        .check(status.is(302))
        .check(css("div.alert alert-danger").is("Invalid credentials"))
      )
      .exec(session => session.set("customerLoggedIn", false))

}