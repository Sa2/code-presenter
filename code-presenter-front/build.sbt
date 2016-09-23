name := """code-presenter-front"""

version := "1.0-SNAPSHOT"

lazy val domain = project.in(file("module/code-presenter-domain")).enablePlugins(PlayScala)
lazy val infrastructure = project.in(file("module/code-presenter-infrastructure"))
lazy val common = project.in(file("module/code-presenter-common"))
  .dependsOn(domain)
lazy val front = (project in file(".")).enablePlugins(PlayScala)
  .dependsOn(domain)
  .dependsOn(infrastructure)
  .dependsOn(common)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
//  jdbc,
//  cache,
//  ws,
  "joda-time" % "joda-time" % "2.9.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)