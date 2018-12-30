package com.tegess.ketsiso.simulationlauncher.simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class TransferMeasureSimulation extends Simulation {

  val httpConf = http
    .baseUrl("http://testserver:8090")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("TransferMeasureSimulation")
    .exec(http("request_1")
      .get("/xd?response_size=200"))
    .pause(5)

  setUp(
    scn.inject(constantUsersPerSec(20) during (10 seconds))
  ).protocols(httpConf)
}
