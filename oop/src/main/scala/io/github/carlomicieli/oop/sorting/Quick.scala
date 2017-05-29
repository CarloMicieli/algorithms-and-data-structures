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

package io.github.carlomicieli.oop.sorting

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.ClassTag

/** Implements the `quick sort` sorting algorithm.
  */
object Quick extends Sorting with LazyLogging {
  def name: String = "Quick Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)(implicit ord: Ordering[A]): Unit = {
    qSort(array, start, end - 1)
  }

  private def qSort[A](A: Array[A], p: Int, r: Int)(implicit ord: Ordering[A]): Unit = {
    if (p < r) {
      val pivot = partition(A, p, r)
      qSort(A, p, pivot - 1)
      qSort(A, pivot + 1, r)
    }
  }

  private def partition[A](A: Array[A], p: Int, r: Int)(implicit ord: Ordering[A]): Int = {
    import Ordered._

    randomSelect(A, p, r)
    val pivot = A(r)
    var i = p - 1

    for (j <- p until r) {
      if (A(j) <= pivot) {
        i = i + 1
        swap(A, i, j)
      }
    }

    swap(A, r, i + 1)
    i + 1
  }

  private def randomSelect[A: Ordering](A: Array[A], p: Int, r: Int): Unit = {

    import scala.util._

    val rnd = new Random
    val pivot = rnd.nextInt(r - p) + p
    swap(A, r, pivot)
  }
}