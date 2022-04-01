package com.qa

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class BaseSimulation extends Simulation {

  val domain = "demostore.gatling.io"

  val rnd = new Random()

  def randomString(length: Int): String = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val initSession = exec(flushCookieJar)
    .exec(session => session.set("randomNumber", rnd.nextInt()))
    .exec(session => session.set("customerLoggedIn", false))
    .exec(addCookie(Cookie("sessionId", randomString(10)).withDomain(domain)))

  val httpProtocol = http
    .baseUrl("http://" + domain )
}
