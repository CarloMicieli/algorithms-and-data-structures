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
package com.carlomicieli.algo

/**
 * @author Carlo Micieli
 */
object Arrays {

  /**
   * Implements Fisher–Yates shuffle
   * http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
   *
   * @param a
   * @tparam T
   */
  def shuffle[T](a: Array[T]): Unit = {
    val rnd = scala.util.Random
    (0 until a.size) foreach { i =>
      val remaining = a.size - i
      val j = rnd.nextInt(remaining) + i
      swap(a, i, j)
    }
  }

  def swap[T](arr: Array[T], i: Int, j: Int): Unit = {
    if (i != j) {
      val temp = arr(i)
      arr(i) = arr(j)
      arr(j) = temp
    }
  }
}