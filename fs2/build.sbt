import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "fs2",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "co.fs2" %% "fs2-core" % "3.0.0",
    libraryDependencies += "co.fs2" %% "fs2-io" % "3.0.0",
    libraryDependencies += "co.fs2" %% "fs2-reactive-streams" % "3.0.0"
  )

