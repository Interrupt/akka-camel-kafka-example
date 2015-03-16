import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

name := """camel-scala"""

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).
  enablePlugins(PlayScala).
  enablePlugins(SbtNativePackager).
  enablePlugins(JavaAppPackaging)

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.akka" %% "akka-camel" % "2.3.8",
  "org.apache.camel" % "camel-kafka" % "2.14.1"
)

dockerBaseImage := "dockerfile/java:oracle-java8"

maintainer := "Ticketfly"

dockerExposedPorts := Seq(9000)

dockerRepository := Some("chad.cuddigan")