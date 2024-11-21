import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "3.6.1"

lazy val root = (project in file("."))
  .settings(
    name := "SoftwareEngineeringJJ",

    version := {
      try {
          sys.process.Process("git describe --tags --abbrev=0").!!.stripLineEnd
      } catch {
          case _: Throwable => "0.0.0" // Default version if no tags are found
      }
    },

    // Define the artifact name dynamically based on the version
    artifactName in (Compile, packageBin) := { (scalaVersion, module, artifactName) =>
        s"${name.value}-${version.value}.jar"
    },
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test",
    libraryDependencies += "junit" % "junit" % "4.13.2" % Test
  )

