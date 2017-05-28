object ScalacOptions {
  val Default: Seq[String] = Seq(
    "-target:jvm-1.8",
    "-encoding", "utf-8", 
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

  val ConsoleDefault: Seq[String] => Seq[String] = _.filterNot(Set(
    "-Ywarn-unused:imports",
    "-Xfatal-warnings"
  ))

  val TestDefault: Seq[String] => Seq[String] = _.filterNot(Set(
    "-Ywarn-unused-import"
  ))
}
