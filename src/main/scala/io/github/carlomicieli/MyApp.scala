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
package io.github.carlomicieli

import com.typesafe.scalalogging.LazyLogging
import io.github.carlomicieli.dst.immutable.Tree
import io.github.carlomicieli.searching.Words

object MyApp extends LazyLogging {
  def main(args: Array[String]): Unit = {

    val words = Words.fromFile("/data/warpeace.txt")

    val tree = sequence(Tree.empty[String, Int])(words.map(w => (w, 1)))

    println(tree.size)
    println(tree.lookup("CHAPTER"))
  }

  def sequence(tree: Tree[String, Int])(elements: Seq[(String, Int)]): Tree[String, Int] = {
    elements match {
      case (k, v) +: rest => sequence(tree.upsert(k, v)(_ + 1))(rest)
      case Seq() => tree
    }
  }

  def timed[U](op: () => U): Long = {
    import java.time._
    import java.time.temporal._
    val start: Instant = Instant.now()
    op()
    val end: Instant = Instant.now()

    start.until(end, ChronoUnit.SECONDS)
  }
}
