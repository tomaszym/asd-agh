import sbt._
import sbt.Keys._
import scala._

object BuildSettings {
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.4"

  val buildSettings = Defaults.defaultSettings ++ Seq(
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
    "com.propensive" %% "rapture-core" % "1.0.0",
    "com.propensive" %% "rapture-io" % "0.10.0"
  )

  val logger = Seq(
    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2")

  def list = List(scalaTest) ++ logger ++ rapture
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
