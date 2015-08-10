package io.github.carlomicieli.sorting

import io.github.carlomicieli.dst.mutable.MaxPQ

import scala.reflect.ClassTag

object Heap extends Sorting {
  def name: String = "Heap Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    val heap = MaxPQ(array)
    for (i <- array.length - 1 to 1 by -1) {
      swap(array, 0, i)
      heap.removeMax()
    }
  }
}
