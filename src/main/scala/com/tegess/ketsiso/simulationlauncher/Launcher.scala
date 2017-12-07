package com.tegess.ketsiso.simulationlauncher

import com.tegess.ketsiso.simulationlauncher.simulations.TestSimulation
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object Launcher extends App {

  val builder = new GatlingPropertiesBuilder()

  builder.sourcesDirectory("src/main/scala")
  builder.binariesDirectory("out/production/classes")
  builder.simulationClass(classOf[TestSimulation].getName)

  Gatling.fromMap(builder.build)

}

