lazy val infrastructure = (project in file("."))

name := """code-presenter-infrastructure"""
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.etaty" %% "rediscala" % "1.6.0"
)