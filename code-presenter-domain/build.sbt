lazy val infrastructure = project.in(file("module/code-presenter-infrastructure"))
lazy val domain = (project in file("."))
  .dependsOn(infrastructure)

name := """code-presenter-domain"""
scalaVersion := "2.11.8"