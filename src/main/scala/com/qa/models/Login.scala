package com.qa.models

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Login {

  lazy val goToLoginPage =
      exec(http("Load Login Page")
        .get("/#{loginButton}")
        .check(status.is(200))
        .check(substring("Username:"))
      .check(css("input[name='username']").saveAs("loginForm"))
      ).exec(session => {
        print(session("loginForm").as[String])
        session
      })

  lazy val loginSuccessful =
      exec(http("Successful Login")
        .post("/#{loginForm}")
        .formParam("_csrf", "#{crsfValue}")
        .formParam("username", "#{username}")
        .formParam("password", "#{password}")
        .check(status.is(200))
      )

  lazy val loginUnsuccessful =
      exec(http("Unsuccessful Login")
        .post("/#{loginForm}")
        .formParam("_csrf", "#{crsfValue}")
        .formParam("username", "#{username}")
        .formParam("password", "#{password}")
        .check(status.is(302))
        .check(css("div.alert alert-danger").is("Invalid credentials"))
      )

}