/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2015 the original author or authors.
 *  See the LICENCE.txt file distributed with this work for additional
 *  information regarding copyright ownership.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.carlomicieli.files

import scala.io.Source
import scala.util.{Success, Try}

case class CsvFile(filename: String, hasTitle: Boolean) {
  import java.io.InputStream

  def parse[A](f: Vector[String] => Try[A]): Try[Seq[A]] = {
    loadFile(filename) map {
      in =>
        Source.fromInputStream(in).getLines()
          .drop(linesToDrop)
          .map(extractTokens andThen f)
          .collect { case Success(v) => v }
          .toList
    }
  }

  private def loadFile(fn: String): Try[InputStream] = {
    Try.apply {
      getClass.getResourceAsStream(fn)
    }
  }

  private def linesToDrop: Int = if (hasTitle) 1 else 0
}

private[this]
object extractTokens extends (String => Vector[String]) {
  def apply(s: String): Vector[String] =
    if (s.isEmpty) Vector.empty[String]
    else {
      @annotation.tailrec
      def go(str: String, acc: Vector[String]): Vector[String] = {
        if (str.isEmpty) acc
        else {
          val (word, remaining) = nextWord(str)
          go(remaining, acc :+ word)
        }
      }

      go(s, Vector.empty[String])
    }

  private def nextWord(s: String): (String, String) =
    if (s.isEmpty) (s, s)
    else {
      s.head match {
        case '"' =>
          val (word, remaining) = s.tail.span(_ != '"')
          (word, remaining.drop(2))
        case _   =>
          val (word, remaining) = s.span(_ != ',')
          (word, remaining.drop(1))
      }
    }
}