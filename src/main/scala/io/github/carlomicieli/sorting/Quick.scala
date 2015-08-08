package io.github.carlomicieli.sorting

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.ClassTag

object Quick extends Sorting with LazyLogging {
  def name: String = "Quick Sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    qSort(array, start, end - 1)
  }

  private def qSort[A](A: Array[A], p: Int, r: Int)
                      (implicit ord: Ordering[A]): Unit = {
    if (p < r) {
      val pivot = partition(A, p, r)
      qSort(A, p, pivot - 1)
      qSort(A, pivot + 1, r)
    }
  }

  private def partition[A](A: Array[A], p: Int, r: Int)
                          (implicit ord: Ordering[A]): Int = {
    import Ordered._

    randomSelect(A, p, r)
    val pivot = A(r)
    var i = p - 1

    for (j <- p until r) {
      if (A(j) <= pivot) {
        i = i + 1
        swap(A, i, j)
      }
    }

    swap(A, r, i + 1)
    i + 1
  }

  private def randomSelect[A](A: Array[A], p: Int, r: Int)
                             (implicit ord: Ordering[A]): Unit = {

    import scala.util._

    val rnd = new Random
    val pivot = rnd.nextInt(r - p) + p
    swap(A, r, pivot)
  }
}
