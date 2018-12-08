package com.tegess.ketsiso.simulationlauncher.api

import com.tegess.ketsiso.simulationlauncher.simulations.{TestSimulation, TransferMeasureSimulation}
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.web.bind.annotation._

@RestController
class GatlingApi {

  @Autowired var resourceLoader: ResourceLoader = _

  val availableTests = Map(
    classOf[TestSimulation].getSimpleName -> classOf[TestSimulation].getName,
    classOf[TransferMeasureSimulation].getSimpleName -> classOf[TransferMeasureSimulation].getName
  )

  @PostMapping(path = Array("gatling/tests/{testName}"))
  def runTest(@PathVariable testName: String) = {
    if (availableTests.contains(testName)) {
      val builder = new GatlingPropertiesBuilder
      builder.simulationClass(availableTests(testName))
      builder.resultsDirectory("static/results")
      Gatling.fromMap(builder.build)
    } else {
      throw new IllegalArgumentException("Sory, but this test is not supported.")
    }
  }

  @PostMapping(path = Array("gatling/test"))
  def test() = {
    val builder = new GatlingPropertiesBuilder()
    builder.simulationClass(classOf[TestSimulation].getName)
    builder.resultsDirectory("static/results")
    Gatling.fromMap(builder.build)
  }

  @GetMapping(path = Array("gatling/results"))
  def listResults() = {
    val root = resourceLoader.getResource("results")
    val result = root.getFile.listFiles().toList.map(_.getName)
    result
  }

  @GetMapping(path = Array("gatling/tests"))
  def getAvailableTests() = {
    availableTests.keys.toList
  }

  @DeleteMapping(path = Array("gatling/results/{id}"))
  def remove(@PathVariable id: String) = {
    FileUtils.deleteDirectory(resourceLoader.getResource(s"results/$id").getFile)
  }


}
