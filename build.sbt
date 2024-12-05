import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "3.6.1"

lazy val root = (project in file("."))
  .settings(
    name := "SoftwareEngineeringJJ",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    libraryDependencies += "junit" % "junit" % "4.13.2" % Test
  )

