lazy val commonSettings = Seq(
  scalaVersion := "2.11.6",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.5" % Test
  )
)

lazy val bstChecker = Project(
  "bst-checker",
  file("bst-checker"),
  settings = commonSettings
)

lazy val sortAlgorithmsBenchmark = Project(
  "sorting-algorithms-benchmark",
  file("sorting-algorithms-benchmark"),
  settings = commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "com.storm-enroute" %% "scalameter" % "0.8.2" % Test
    ),
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    logBuffered := false,
    parallelExecution in Test := false
  )
)
