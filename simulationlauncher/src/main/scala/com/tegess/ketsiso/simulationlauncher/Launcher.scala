package com.tegess.ketsiso.simulationlauncher

import com.tegess.ketsiso.simulationlauncher.api.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler, ServletHolder}

object Launcher {

  def main(args: Array[String]): Unit = {
    val server = new Server(8080)

    val handler = new ServletContextHandler()

    val resourcesHandler = new ServletHolder("resources", classOf[DefaultServlet])
    resourcesHandler.setInitParameter("resourceBase", "")
    resourcesHandler.setInitParameter("dirAllowed", "true")
    resourcesHandler.setInitParameter("pathInfoOnly", "true")

    server.setHandler(handler)

    handler.addServlet(classOf[Handler], "/*")
    handler.addServlet(resourcesHandler, "/resources/*")

    server.start()
    server.join()

  }

}

