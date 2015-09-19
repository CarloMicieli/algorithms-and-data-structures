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
package io.github.carlomicieli.sorting

import com.typesafe.scalalogging.LazyLogging

object SortRunner extends SampleData with LazyLogging {

  private type TestCase = (Sorting, Array[Int])
  private type TestCases = Seq[TestCase]

  def apply(problemSize: Int, sortAlgorithm: Sorting): TestResult = {
    val test = (sortAlgorithm, randomIntArray(problemSize))
    runTest(test)
  }

  def apply(problemSizes: Seq[Int], sortAlgorithms: Seq[Sorting]): Seq[TestResult] = {
    testCases(problemSizes, sortAlgorithms).map(runTest)
  }

  private def runTest(t: TestCase): TestResult = {
    val (algo, array) = t
    TestResult(algo.name, withLogging(algo, array), array.length)
  }

  // TRIVIAL IMPLEMENTATION: NOT a real micro-benchmark!!!
  private def timed[U](op: => U): Long = {
    import java.time._
    import java.time.temporal._
    val start: Instant = Instant.now()
    op
    val end: Instant = Instant.now()

    start.until(end, ChronoUnit.SECONDS)
  }

  private def withLogging(sorting: Sorting, arr: Array[Int]): Long = {
    timed {
      logger.debug(s"*Before* ${sorting.printArray(arr)}")
      sorting.sort(arr)
      logger.debug(s"Array is now sorted? ${arr.isSorted}")
      logger.debug(s"*After*  ${sorting.printArray(arr)}")
    }
  }

  private def testCases(sizes: Seq[Int], algorithms: Seq[Sorting]): TestCases = {
    for {
      algo <- algorithms
      size <- sizes
    } yield (algo, randomIntArray(size))
  }
}

case class TestResult(algorithmName: String, runTime: Long, size: Int)