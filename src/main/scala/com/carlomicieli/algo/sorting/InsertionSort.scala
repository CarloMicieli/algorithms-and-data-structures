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

/**
 * @author Carlo Micieli
 */
class InsertionSort extends SortAlgorithm {

  override val name: String = "InsertionSort"

  def sort[A](arr: Array[A])(implicit ord: math.Ordering[A]): Array[A] = {
    var i, j = 0
    val end = arr.size

    while (i < end) {
      j = i
      while (j > 0 && less(arr, j, j - 1)) {
        swap(arr, j, j - 1)
        j = j - 1
      }
      i = i + 1
    }

    arr
  }
}
