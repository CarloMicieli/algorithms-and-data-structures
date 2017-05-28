val scalaTestVersion = "2.2.6"
val scalaCheckVersion = "1.12.5"

val headerSettings = Seq(
  headerLicense := Some(HeaderLicense.Custom(
    """|                    __                  __
       |   ______________ _/ /___ _      ____ _/ /___ _____
       |  / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
       | (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
       |/____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
       |                  /____/
       |Copyright (c) 2017 the original author or authors.
       |See the LICENCE.txt file distributed with this work for additional
       |information regarding copyright ownership.
       |
       |Licensed under the Apache License, Version 2.0 (the "License");
       |you may not use this file except in compliance with the License.
       |You may obtain a copy of the License at
       |
       |  http://www.apache.org/licenses/LICENSE-2.0
       |
       |Unless required by applicable law or agreed to in writing, software
       |distributed under the License is distributed on an "AS IS" BASIS,
       |WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       |See the License for the specific language governing permissions and
       |limitations under the License.
       |""".stripMargin
  ))
)

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
  .settings(headerSettings)
  .enablePlugins(AutomateHeaderPlugin)
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
