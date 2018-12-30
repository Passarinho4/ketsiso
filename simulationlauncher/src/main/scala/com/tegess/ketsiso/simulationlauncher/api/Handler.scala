package com.tegess.ketsiso.simulationlauncher.api

import com.tegess.ketsiso.simulationlauncher.simulations.{TestSimulation, TransferMeasureSimulation}
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import org.apache.commons.io.FileUtils
import org.springframework.core.io.{DefaultResourceLoader, ResourceLoader}

class Handler extends HttpServlet {

  private val gatlingBuilder = new GatlingPropertiesBuilder()
  private val availableTests = Map(
    classOf[TestSimulation].getSimpleName -> classOf[TestSimulation].getName,
    classOf[TransferMeasureSimulation].getSimpleName -> classOf[TransferMeasureSimulation].getName
  )
  private val resourceLoader: ResourceLoader = new DefaultResourceLoader()

  override def doOptions(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setHeader("Access-Control-Allow-Origin", "*")
    resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
    resp.setHeader("Access-Control-Allow-Headers", "Authorization, accept")
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, X-AUTH-TOKEN")
    resp.setHeader("Access-Control-Max-Age", "1500")

  }

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    prepareResponse(resp)
    if (req.getPathInfo == "/tests") {
      resp.getWriter.print(listToJson(availableTests.keys.toList))
    } else if (req.getPathInfo == "/results") {
      val root = resourceLoader.getResource("file:static/results")
      val result = root.getFile.listFiles().toList.map(_.getName)
      resp.getWriter.print(listToJson(result))
    }
  }

  def prepareResponse(resp: HttpServletResponse) = {
    resp.setHeader("Access-Control-Allow-Origin", "*")
    resp.setContentType("application/json")
  }

  def listToJson(list: List[String]): String = {
    val s = list.map(s => "\"" + s + "\"").mkString(",")
    s"[$s]"
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val testName = req.getParameter("testName")
    if (availableTests.contains(testName)) {
      if (testName == classOf[TransferMeasureSimulation].getSimpleName) {
        TransferMeasureSimulation.handle(req)
      }
      val builder = new GatlingPropertiesBuilder
      builder.simulationClass(availableTests(testName))
      builder.resultsDirectory("static/results")
      Gatling.fromMap(builder.build)
    } else {
      throw new IllegalArgumentException("Sory, but this test is not supported.")
    }
  }

  override def doDelete(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val resultId = req.getParameter("id")
    FileUtils.deleteDirectory(resourceLoader.getResource(s"file:static/results/$resultId").getFile)
  }
}


