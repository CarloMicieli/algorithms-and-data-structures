package io.github.carlomicieli.sorting

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.ClassTag

object Bubble extends Sorting with LazyLogging {
  def name: String = "Bubble Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    import Ordered._
    for (i <- start until end) {
      for (j <- start until end - 1) {
        if (array(j + 1) < array(j))
          swap(array, j, j + 1)
      }
    }
  }
}
