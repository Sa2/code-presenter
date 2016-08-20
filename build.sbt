// 開発用のbuild.sbt
// dist時はそれぞれのプロジェクトですること。
// よってそれぞれのプロジェクトのbuild.sbtにも依存性解決を記載する必要がある。


lazy val domain = project.in(file("code-presenter-domain"))
lazy val infrastructure = project.in(file("code-presenter-infrastructure"))
lazy val stream = project.in(file("code-presenter-stream"))
  .dependsOn(domain)
  .dependsOn(infrastructure)
lazy val front = project.in(file("code-presenter-front")).enablePlugins(PlayScala)
  .dependsOn(domain)
  .dependsOn(infrastructure)
lazy val root = (project in file("."))

name := """code-presenter"""

version := "1.0"

scalaVersion := "2.11.8"