lazy val infrastructure = project.in(file("module/code-presenter-infrastructure"))
lazy val domain = (project in file(".")).enablePlugins(PlayScala)
  .dependsOn(infrastructure)

name := """code-presenter-domain"""
scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.0"
)