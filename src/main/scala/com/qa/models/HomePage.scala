package com.qa.models

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object HomePage {

  lazy val getHomePage =
    http("getHomePage")
      .get("/")
      .check(status.is(200))
      .check(css("#_csrf", "content").saveAs("crsfValue"))
      .check(css("a[href='/about-us']").saveAs("aboutUsButton"))
      .check(css("a[href='/login']").saveAs("loginButton"))
      .check(css("a[href='/category/all']").saveAs("allCategoriesButton"))

  lazy val  getAboutUs =
    exec(
      http("getAboutUs")
        .get("/#{aboutUsButton}")
        .check(status.is(200))
        .check(css("div.col-7 h2").is("About Us"))
    )

}