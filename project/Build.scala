import sbt._
import sbt.Keys._
import scala._

object BuildSettings {
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.2"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    libraryDependencies ++= Dependencies.list,
    resolvers ++= Resolvers.list
  )

}

object Resolvers {

  lazy val list = List()
}

object Dependencies {


  val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

  val logger = Seq(
    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2")

  def list = List(scalaTest) ++ logger
}

object AsdAGHBuild extends Build {

  import BuildSettings._

  val projectName = "asd-agh"

  lazy val trelloilaro = Project(
    id = projectName,
    base = file("."),
    settings = buildSettings ++ Seq(name := projectName)
  )
}
