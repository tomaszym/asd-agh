import sbt._
import sbt.Keys._
import scala._
import scala.scalajs.sbtplugin
import scala.scalajs.sbtplugin.ScalaJSPlugin
import scala.scalajs.sbtplugin.ScalaJSPlugin._

object BuildSettings {
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.4"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    libraryDependencies ++= Dependencies.list,
    resolvers ++= Resolvers.list,
    testOptions in Test += Tests.Argument("-oD")
  )

}

object Resolvers {

  lazy val list = List()
}

object Dependencies {

  lazy val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

  lazy val rapture = Seq(
    "com.propensive" %% "rapture-core" % "1.1.0",
    "com.propensive" %% "rapture-io" % "0.10.0",
    "com.propensive" %% "rapture-fs" % "0.10.0"
  )

  val logger = Seq(
    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2")

  def list = List(scalaTest) ++ logger ++ rapture
}

object AsdAGHBuild extends Build {

  import BuildSettings._

  val rootName = "asd-agh-root"
  val projectName = "asd-agh"
  val jsName = "js-asd-agh"

  lazy val root = Project(
    id = rootName,
    base = file("."),
    settings = buildSettings ++ Seq(name := rootName)
  ).aggregate(jsAsd)

  lazy val asd = Project(
    id = projectName,
    base = file(projectName),
    settings = buildSettings ++ Seq(name := projectName)
  )

  lazy val jsAsd = Project(
    id = jsName,
    base = file(jsName),
    settings = JsBuildSettings.buildSettings ++ ScalaJSPlugin.scalaJSSettings ++ Seq(
      name := jsName,
      libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"
    )
  ).dependsOn(asd)

}
