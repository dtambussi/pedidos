import aether.MavenCoordinates
import aether.AetherPlugin.createArtifact
import aether.AetherKeys._

name := "pedidos"

organization := "com.devmen"

version := {
  if (Option(System.getProperty("version")).nonEmpty) {
    System.getProperty("version")
  } else "1.0"
}

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

// Dependencies
libraryDependencies ++= Seq(
  jdbc,
  ws,
  cache,
  evolutions,
  "com.typesafe.play" %% "anorm" % "2.5.0",
  "com.github.nscala-time" %% "nscala-time" % "2.8.0",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "com.softwaremill.macwire" %% "macros" % "2.2.3" % "provided",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test",
  "commons-io" % "commons-io" % "2.4"
)

routesGenerator := InjectedRoutesGenerator

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")
