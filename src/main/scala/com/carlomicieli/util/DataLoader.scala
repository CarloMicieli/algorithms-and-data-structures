/*
 * Copyright 2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the 'License');
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an 'AS IS' BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.carlomicieli.util

import scala.io.Source
import scala.util.{Try, Success, Failure}

/**
 * @author Carlo Micieli
 */
object DataLoader {

  def loadData(filename: String): Array[Int] = {
    loadFile(filename) match {
      case Success(v) => v.filterNot(_.startsWith("#")).map(_.toInt).toArray
      case Failure(ex) => throw new Exception("File not found " + filename)
    }
  }

  private def loadFile(filename: String): Try[Vector[String]] = {
    Try(Source.fromURL(getClass.getResource(filename)))
      .map(file => file.getLines().toVector)
  }
}
