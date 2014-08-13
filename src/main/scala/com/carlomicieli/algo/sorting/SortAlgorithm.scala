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

import java.time.LocalTime

/**
 * @author Carlo Micieli
 */
trait SortAlgorithm {

  val name: String

  def run[T](problem: InputProblem[T])(implicit ord: math.Ordering[T]): Result[T] = {
    val start = System.nanoTime()
    val sortedArray = sort(problem.array)
    val time = (System.nanoTime() - start) / 1000000

    val result = Result(name, problem.size, time, sortedArray)
    println(result)
    result
  }

  def sort[A](arr: Array[A])(implicit ord: math.Ordering[A]): Array[A]

  def swap[A](arr: Array[A], i: Int, j: Int): Unit = {
    if (i != j) {
      val tmp = arr(i)
      arr(i) = arr(j)
      arr(j) = tmp
    }
  }

  def less[A](arr: Array[A], i: Int, j: Int)(implicit ord: math.Ordering[A]): Boolean = {
    ord.compare(arr(i), arr(j)) < 0
  }

  def less[A](a: A, b: A)(implicit ord: math.Ordering[A]): Boolean = {
    ord.compare(a, b) < 0
  }

  def isSorted[A](arr: Array[A])(implicit ord: math.Ordering[A]): Boolean = {
    type Pair = (A, Boolean)
    val initAcc: Pair = (arr.head, true)
    val op = (acc: Pair, n: A) => (n, acc._2 && ord.compare(acc._1, n) <= 0)
    val (_, sorted) = arr.foldLeft(initAcc)(op)
    sorted
  }
}

case class Result[T](name: String, size: Int, time: Long, sortedArray: Array[T]) {
  def seconds: Long = time / 1000

  override def toString = s"${LocalTime.now()}] Result($name, $size, $totalTime, $sortedSample)"
  def sortedSample = sortedArray.take(10).mkString("[", ", ", " ...]")
  def totalTime = if (seconds == 0) s"$time ms" else s"$seconds sec"
}