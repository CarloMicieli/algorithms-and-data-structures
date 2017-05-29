/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
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

/** Implements the `merge sort` sorting algorithm.
  */
object Merge extends Sorting with LazyLogging {
  def name: String = "Merge Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)(implicit ord: Ordering[A]): Unit = {
    val aux = new Array[A](array.length)
    mSort(array, aux, start, end)(ord)
  }

  private def mSort[A](A: Array[A], aux: Array[A], start: Int, end: Int)(implicit ord: Ordering[A]): Unit = {
    import Ordered._

    logger.debug(s"mSort($start, $end)")

    val size = end - start
    if (size <= 1) ()
    else {
      val mid = size / 2 + start
      mSort(A, aux, start, mid)
      mSort(A, aux, mid, end)

      if (A(mid - 1) > A(mid))
        merge(A, aux, start, mid, end)
      else
        logger.debug(s"Skip merge(). Nothing to do [${A(mid - 1)} < ${A(mid)}}]")
    }
  }

  private def merge[A](A: Array[A], aux: Array[A], start: Int, mid: Int, end: Int)(implicit ord: Ordering[A]): Unit = {
    import Ordered._

    for (k <- start until end) {
      aux(k) = A(k)
    }

    var i = start
    var j = mid
    for (k <- start until end) {
      if (i >= mid) {
        A(k) = aux(j)
        j = j + 1
      } else if (j >= end) {
        A(k) = aux(i)
        i = i + 1
      } else if (aux(i) <= aux(j)) {
        A(k) = aux(i)
        i = i + 1
      } else {
        A(k) = aux(j)
        j = j + 1
      }
    }
  }
}