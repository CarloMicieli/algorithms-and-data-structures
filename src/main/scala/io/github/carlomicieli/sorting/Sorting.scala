package io.github.carlomicieli.sorting

import scala.reflect.ClassTag

trait Sorting {
  def name: String

  def sort[A: ClassTag](array: Array[A])(implicit ord: Ordering[A]): Unit = {
    sort[A](array, 0, array.length)
  }

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)(implicit ord: Ordering[A]): Unit

  def isSorted[A](array: Array[A])(implicit ord: Ordering[A]): Boolean = {
    array match {
      case Array() | Array(_) => true
      case _ => {
        @annotation.tailrec
        def loop(i: Int): Boolean = {
          if (i == array.length) true
          else {
            if (ord.gt(array(i - 1), array(i))) false
            else loop(i + 1)
          }
        }
        loop(1)
      }
    }
  }

  def printArray[A](array: Array[A]): String = {
    array.mkString("[", ", ", "]")
  }

  protected def swap[A](array: Array[A], i: Int, j: Int): Unit = {
    if (i != j) {
      val tmp: A = array(i)
      array(i) = array(j)
      array(j) = tmp
    }
  }
}
