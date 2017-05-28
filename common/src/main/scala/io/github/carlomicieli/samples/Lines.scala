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

package io.github.carlomicieli.samples

import sys.process._
import java.net.URL
import java.nio.file.{ Files, Path, Paths }

import scala.language.postfixOps
import scala.io.Source
import scala.util.Try

object Lines {
  def fromUrl(filename: String, url: String): Stream[String] = {
    val file = Paths.get(filename)
    linesFromUrl(url, file).map(l => l.toStream).get
  }

  type Lines = Iterator[String]

  private def linesFromUrl(url: String, destPath: Path): Try[Lines] = {
    Try {
      val file = destPath.toFile
      if (!Files.exists(destPath)) {
        new URL(url) #> file !!
      }

      Source.fromFile(file).getLines()
    }
  }
}
