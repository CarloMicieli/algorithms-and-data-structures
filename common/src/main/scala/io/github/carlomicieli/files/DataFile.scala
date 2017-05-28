/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                   /____/
 * Copyright (c) 2017 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.carlomicieli.files

import scala.io.Source
import scala.util.Try

class DataFile private (filename: String) {
  import java.io.InputStream

  def parse[A](f: ParsingFunction[A]): Try[Stream[A]] = {
    loadFile(filename) map {
      in =>
        Source.fromInputStream(in).getLines()
          .toStream
          .zip(Stream.from(1))
          .map(p => f.tupled(p.swap))
          .collect { case Matched(v) => v }
    }
  }

  private def loadFile(fn: String): Try[InputStream] = {
    Try.apply {
      getClass.getResourceAsStream(fn)
    }
  }
}

object DataFile {
  def apply[A](filename: String)(f: ParsingFunction[A]): Stream[A] = {
    val dat = new DataFile(filename)
    dat.parse(f).get
  }
}