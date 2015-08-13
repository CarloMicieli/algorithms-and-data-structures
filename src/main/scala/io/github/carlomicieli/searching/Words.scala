package io.github.carlomicieli.searching

import scala.io.Source

object Words {
  def fromFile(filename: String): Seq[String] = {
    val pattern = """(\w+)""".r
    lines(filename).flatMap(l => pattern.findAllIn(l)).toList
  }

  private def lines(filename: String): Iterator[String] = {
    val stream = getClass.getResourceAsStream(filename)
    Source.fromInputStream(stream).getLines()
  }
}