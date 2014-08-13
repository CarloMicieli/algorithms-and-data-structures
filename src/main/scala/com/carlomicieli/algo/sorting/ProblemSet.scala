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
package com.carlomicieli.algo.sorting

import com.carlomicieli.util.DataLoader

import scala.util.Random

/**
 * @author Carlo Micieli
 */
object ProblemSet {

  def solveAll(algorithm: SortAlgorithm): String = {
    warmUp(algorithm)
    problemsSet
      .map(size => algorithm.run(problem(size))).mkString("\n")
  }

  def problemsSet: Seq[Int] = {
    Seq(16, 256, 4096, 32768, 65536, 262144, 524288, 1048576)
  }

  private def problem(size: Int) = {
    val array = DataLoader.loadData(s"/test-data/set$size.txt")
    InputProblem(size, array)
  }

  private def warmUp(algorithm: SortAlgorithm) = {
    val rnd = new Random()
    val numbers: Array[Int] = (1 to 16).toList.map(n => rnd.nextInt(50)).toArray
    (1 to 10) foreach {
      algorithm.sort(numbers)
    }
  }
}

case class InputProblem[T](size: Int, array: Array[T])