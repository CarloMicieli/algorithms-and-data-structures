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

package io.github.carlomicieli.oop.searching

/** Implements fundamental functions for searching.
  */
object Search {

  /** `O(lg n)` Search for an element in a sorted `Array`.
    *
    * @param xs a sorted `Array`
    * @param x the element to find
    * @param ord the element ordering
    * @tparam A the element type
    * @return optionally the index for the found element
    */
  def binarySearch[A](xs: Array[A], x: A)(implicit ord: Ordering[A]): Option[Int] = {
    binarySearch(xs, x, 0, xs.length)(ord)
  }

  /** `O(lg n)` Search for an element in a part of a sorted `Array`.
    *
    * @param xs a sorted `Array`
    * @param x the element to find
    * @param lo the low index
    * @param hi the high index
    * @param ord the element ordering
    * @tparam A the element type
    * @return optionally the index for the found element
    */
  def binarySearch[A](xs: Array[A], x: A, lo: Int, hi: Int)(implicit ord: Ordering[A]): Option[Int] = {
    import Ordered._
    if (lo < hi) {
      val mid: Int = lo + (hi - lo) / 2
      x compare xs(mid) match {
        case 0          => Some(mid)
        case n if n < 0 => binarySearch(xs, x, lo, mid)
        case n if n > 0 => binarySearch(xs, x, mid + 1, hi)
      }
    } else {
      None
    }
  }
}