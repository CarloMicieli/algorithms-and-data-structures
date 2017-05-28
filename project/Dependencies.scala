import sbt._

object Version {
  final val Scala      = "2.11.7"
  final val ScalaTest  = "2.2.6"
  final val ScalaCheck = "1.12.5"
  final val ScalaMeter = "0.7"
}

object Library {
  val Logback     : ModuleID = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val ScalaLogging: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  val ScalaTest   : ModuleID = "org.scalatest"     %% "scalatest"  % Version.ScalaTest
  val ScalaCheck  : ModuleID = "org.scalacheck"    %% "scalacheck" % Version.ScalaCheck
  val ScalaMeter  : ModuleID = "com.storm-enroute" %% "scalameter" % Version.ScalaMeter
}
