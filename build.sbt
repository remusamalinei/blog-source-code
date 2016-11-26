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
