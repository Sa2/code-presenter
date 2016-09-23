lazy val common = (project in file("."))

name := """code-presenter-common"""
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.0"
)