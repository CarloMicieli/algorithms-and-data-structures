val scalaTestVersion = "2.2.4"
val scalaCheckVersion = "1.12.5"

val commonSettings = Seq(
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  organization := "io.github.carlomicieli",
  autoAPIMappings := true
)

def ScalaProject(name: String): Project = {
  Project(name, file(name)).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-unused-import",
      "-Ywarn-infer-any",
      "-Ywarn-value-discard"
    )
  )
}

lazy val testUtils = Project("testUtils", file("testUtils")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % scalaTestVersion,
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion
    )
  )

lazy val common = ScalaProject("common").
  dependsOn(testUtils % "test->compile")

lazy val fp = ScalaProject("fp").
  dependsOn(common).
  dependsOn(testUtils % "test->compile")

lazy val oop = ScalaProject("oop").
  dependsOn(common).
  dependsOn(testUtils % "test->compile")

lazy val Benchmark = config("bench") extend Test
lazy val benchmarks = Project("benchmarks", file("benchmarks")).
  dependsOn(fp).
  dependsOn(oop).
  settings(
    scalaVersion := "2.11.7",
    libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.7",
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    logBuffered := false,
    parallelExecution in Test := false
  ) configs Benchmark settings (
  inConfig(Benchmark)(Defaults.testSettings): _*
)

import com.typesafe.sbt.SbtGit.{GitKeys => git}
val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(unidocSettings: _*).
  settings(site.settings ++ ghpages.settings: _*).
  settings(
    name := "algorithms-and-data-structures",
    site.addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), "latest/api"),
    git.gitRemoteRepo := "git@github.com:CarloMicieli/algorithms-and-data-structures.git"
  ).
  aggregate(common, fp, oop)
