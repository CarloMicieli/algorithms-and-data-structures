import sbt._

object Version {
  final val ScalaTest      = "3.0.1"
  final val ScalaCheck     = "1.13.4"
  final val ScalaLogging   = "3.5.0"
  final val TypesafeConfig = "1.3.1"
  final val Logback        = "1.1.7"
  final val ScalaMeter     = "0.7"
}

object Library {
  val Logback     : ModuleID = "ch.qos.logback" % "logback-classic" % Version.Logback
  val ScalaLogging: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % Version.ScalaLogging
  val ScalaTest   : ModuleID = "org.scalatest"     %% "scalatest"  % Version.ScalaTest
  val ScalaCheck  : ModuleID = "org.scalacheck"    %% "scalacheck" % Version.ScalaCheck
  val ScalaMeter  : ModuleID = "com.storm-enroute" %% "scalameter" % Version.ScalaMeter
}

object scalac {
  final val `2.12.1` = "2.12.1"
}