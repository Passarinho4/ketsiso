package com.tegess.ketsiso.simulationlauncher.simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import javax.servlet.http.HttpServletRequest

import scala.collection.mutable
import scala.concurrent.duration._
import scala.language.postfixOps

class TransferMeasureSimulation extends Simulation {

  //val nodeIP = System.getenv("NODE_IP")

  val l5dHost = System.getenv("NODE_IP")

  println(s"Szymek [$l5dHost]")

  val httpConf = http
    .baseUrl(s"http://$l5dHost:4140/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .header("Host", "testserver")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("TransferMeasureSimulation")
    .exec(http("request_1")
      .get(s"/xd?response_size=${TransferMeasureSimulation.getResponseSize}"))
    .pause(5)

  setUp(
    scn.inject(constantUsersPerSec(TransferMeasureSimulation.getUsersPerSec) during TransferMeasureSimulation.getDuring)
  ).protocols(httpConf)
}

object TransferMeasureSimulation {
  val responseSize = "RESPONSE_SIZE"
  val usersPerSec = "USERS_PER_SEC"
  val during = "DURING"

  val params = new mutable.HashMap[String, String]()

  params.put(responseSize, "200")
  params.put(usersPerSec, "20")
  params.put(during, "10")

  def getResponseSize = params(responseSize).toInt

  def getUsersPerSec = params(usersPerSec).toInt

  def getDuring = params(during).toInt seconds

  def handle(req: HttpServletRequest) = {
    Option(req.getParameter(responseSize))
      .foreach(size => params.put(responseSize, size))
    Option(req.getParameter(usersPerSec))
      .foreach(users => params.put(usersPerSec, users))
    Option(req.getParameter(during))
      .foreach(d => params.put(during, d))
  }
}