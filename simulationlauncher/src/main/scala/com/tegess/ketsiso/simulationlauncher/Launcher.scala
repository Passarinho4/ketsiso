package com.tegess.ketsiso.simulationlauncher

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.gatling.core.config.GatlingPropertiesBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

object Launcher extends App {

  SpringApplication.run(classOf[MainConfig])
}

@EnableAutoConfiguration
@Configuration
@ComponentScan
class MainConfig {

  @Bean
  def gatlingBuilder() = new GatlingPropertiesBuilder()

  @Bean
  def mapper: ObjectMapper = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper
  }
}

