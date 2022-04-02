package com.qa.models

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Catalog {
  val categoryFeeder = csv("data/categoryDetails.csv").random
  val jsonFeederProducts = jsonFile("data/productDetails.json").random

  object Category {
    def view = {
        exec(http("Load Category Page")
          .get("#{allCategoriesButton}")
          .check(status.is(200))
          .check(css("a[href='/product/casual-black-blue']").saveAs("casualBlackGlassesButton"))
        ).exec(session => {
        print(session("casualBlackGlassesButton").as[String])
        session
      })
    }
  }

  object Product {
    def view = {
        exec(http("Load Product Page")
          .get("#{casualBlackGlassesButton}")
          .check(status.is(200))
          .check(css("a[data-id='17']").saveAs("addToCart"))
        ).exec(session => {
          print(session("addToCart").as[String])
          session
        })
    }

    def add = {
      exec(view)
        .exec(http("Add Product to Cart")
          .get("#{addToCart}")
          .check(status.is(200))
          .check(css("a[href='/cart/view']").saveAs("viewCart"))
        ).exec(session => {
        print(session("viewCart").as[String])
        session
      })
    }
  }
}
