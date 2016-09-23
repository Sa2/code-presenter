name := """code-presenter-stream"""

version := "1.0"

scalaVersion := "2.11.8"

lazy val domain = project.in(file("module/code-presenter-domain"))
lazy val infrastructure = project.in(file("module/code-presenter-infrastructure"))
lazy val stream = (project in file("."))
  .dependsOn(domain)
  .dependsOn(infrastructure)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.10",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.10",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.10",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.10" % "test",
  "com.typesafe.play" %% "play" % "2.5.8",
  "com.lihaoyi" %% "upickle" % "0.2.8",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")
