name := """code-presenter-front"""

version := "1.0-SNAPSHOT"

lazy val domain = project.in(file("module/code-presenter-domain"))
lazy val infrastructure = project.in(file("module/code-presenter-infrastructure"))
lazy val front = (project in file(".")).enablePlugins(PlayScala)
  .dependsOn(domain)
  .dependsOn(infrastructure)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
//  jdbc,
//  cache,
//  ws,
//  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)