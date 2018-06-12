name := "verspaetung"
organization := "de.mobimeo"
version := "1.0-SNAPSHOT"

scalaVersion := "2.12.6"

routesGenerator := InjectedRoutesGenerator

scapegoatIgnoredFiles := Seq(s"${target.value}.*.scala")
scapegoatVersion in ThisBuild := "1.3.2"
coverageExcludedPackages := "<empty>;router"

libraryDependencies ++= Seq(
  guice, ws,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",

  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  //  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

topLevelDirectory := Some(packageName.value)
