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
object binarySearch {

  /** `O(lg n)` Search for an element in a sorted `Array`.
    *
    * @param xs a sorted `Array`
    * @param x the element to find
    * @tparam A the element type
    * @return optionally the index for the found element
    */
  def apply[A: Ordering](xs: Array[A], x: A): Option[Int] = {
    apply(xs, x, 0, xs.length)
  }

  /** `O(lg n)` Search for an element in a part of a sorted `Array`.
    *
    * @param xs a sorted `Array`
    * @param x the element to find
    * @param low the low index
    * @param high the high index
    * @tparam A the element type
    * @return optionally the index for the found element
    */
  def apply[A: Ordering](xs: Array[A], x: A, low: Int, high: Int): Option[Int] = {
    import Ordered._

    @annotation.tailrec
    def find(l: Int, h: Int): Option[Int] = {
      if (l >= h) {
        None
      } else {
        val mid = (l + h) / 2
        val v = xs(mid)
        if (v == x)
          Some(mid)
        else if (v < x)
          find(mid + 1, h)
        else
          find(l, mid)
      }
    }

    find(low, high)
  }
}