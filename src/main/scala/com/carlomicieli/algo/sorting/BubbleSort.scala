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
class BubbleSort extends SortAlgorithm {
  override val name: String = "BubbleSort"

  override def sort[A](arr: Array[A])(implicit ord: Ordering[A]): Array[A] = {
    var sorted = false
    var i, j = 0
    var end = arr.size - 1

    while (!sorted) {
      sorted = true
      i = 0
      while (i < end) {
        j = i + 1
        if (less(arr, j, i)) {
          swap(arr, i, j)
          sorted = false
        }

        i = i + 1
      }
      end = end - 1
    }

    arr
  }
}
