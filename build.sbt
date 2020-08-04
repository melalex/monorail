import Dependencies._

name := "monorail"
version := "0.1"
scalaVersion := "2.13.3"

lazy val root = (project in file("."))
  .enablePlugins()
  .settings(
    libraryDependencies ++= Seq(
      scalaTest % Test
    )
  )

