package com.carlomicieli.algo.sorting

/**
 * @author Carlo Micieli
 */
class SelectionSort extends SortAlgorithm {
  override val name: String = "SelectionSort"

  def sort[A](arr: Array[A])(implicit ord: Ordering[A]): Array[A] = {
    for(i <- 0 until arr.size) {
      var min = i
      for (j <- i + 1 until arr.size) {
        if (less(arr, j, min)) {
          min = j
        }
      }

      swap(arr, min, i)
    }

    arr
  }
}
