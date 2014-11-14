resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-atmos-play" % "0.3.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-atmos" % "0.3.2")


addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.2")
