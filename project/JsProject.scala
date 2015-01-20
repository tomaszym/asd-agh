import sbt._
import sbt.Keys._
import scala._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys

import scala.scalajs.sbtplugin.ScalaJSPlugin._

object JsBuildSettings {
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.4"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    libraryDependencies ++= JsDependencies.list,
    resolvers ++= JsResolvers.list,
    testOptions in Test += Tests.Argument("-oD"),
    ScalaJSKeys.persistLauncher in Compile := true,
    ScalaJSKeys.persistLauncher in Test := false
  )

}

object JsResolvers {

  lazy val list = List()
}

object JsDependencies {
//
  lazy val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
//
//  lazy val rapture = Seq(
//    "com.propensive" %% "rapture-core" % "1.1.0",
//    "com.propensive" %% "rapture-io" % "0.10.0",
//    "com.propensive" %% "rapture-fs" % "0.10.0"
//  )
//
//  val logger = Seq(
//    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
//    "ch.qos.logback" % "logback-classic" % "1.1.2")

  def list = List(scalaTest)
}
