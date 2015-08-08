package io.github.carlomicieli.sorting

import scala.reflect.ClassTag

object Selection extends Sorting {
  def name: String = "Selection Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    import Ordered._
    for(i <- start until end) {
      var min = i
      for (j <- i + 1 until end) {
        if (array(j) < array(min)) {
          min = j
        }
      }

      swap(array, min, i)
    }
  }
}
