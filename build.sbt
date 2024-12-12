import sbt.Keys.{javaOptions, *}

ThisBuild / scalaVersion := "3.6.1"

lazy val root = (project in file("."))
  .settings(
    name := "SoftwareEngineeringJJ",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "org.openjfx" % "javafx-base" % "17" classifier "win",
      "org.openjfx" % "javafx-controls" % "17" classifier "win",
      "org.openjfx" % "javafx-fxml" % "17" classifier "win",
      "org.openjfx" % "javafx-graphics" % "17" classifier "win",
      "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
      "junit" % "junit" % "4.13.2" % Test
    ),
    fork := true,
    javaOptions ++= Seq(
      "--module-path",
      "C:\\SE\\openjfx-17.0.13_windows-x64_bin-sdk\\javafx-sdk-17.0.13\\lib",
      "--add-modules",
      "javafx.controls,javafx.fxml"
    )

  )