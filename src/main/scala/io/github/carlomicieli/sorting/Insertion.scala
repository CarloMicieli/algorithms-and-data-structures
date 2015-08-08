package io.github.carlomicieli.sorting

import scala.reflect.ClassTag

object Insertion extends Sorting {
  def name: String = "Insertion Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    import Ordered._
    for (i <- start + 1 until end) {
      val el = array(i)
      var j = i - 1
      while (j >= start && el < array(j)) {
        array(j + 1) = array(j)
        j = j - 1
      }

      array(j + 1) = el
    }
  }
}
