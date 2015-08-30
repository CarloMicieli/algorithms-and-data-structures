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
import io.github.carlomicieli.searching.BinarySearchST

object MyApp extends LazyLogging {
  def main(args: Array[String]): Unit = {

    val st = BinarySearchST[String, Int](16)
    st("hello") = 1
    println(st.toString)



    /*
    val st = SequentialSearchST.empty[String, Int]

    val dur = timed { () => {
        val output = FrequencyCounter("/data/tale.txt", 8)(st)
        println(output.mkString("\n"))
      }
    }

    println(dur + " seconds")
    */
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
