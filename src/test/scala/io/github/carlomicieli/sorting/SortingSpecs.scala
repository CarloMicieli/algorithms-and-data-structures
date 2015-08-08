package io.github.carlomicieli.sorting

import org.scalatest.{FlatSpec, Matchers}

import scala.reflect.ClassTag

class SortingSpecs extends FlatSpec with Matchers {
  import NopSorting._

  it should "convert arrays to strings" in {
    val s = printArray(Array(1, 2, 3))
    s should be("[1, 2, 3]")
  }

  it should "check whether an array is sorted" in {
    val sorted = Array(1, 2, 3, 44, 76)
    val unsorted = Array(1, 56, 15, 42, 67)

    isSorted(sorted) should be(true)
    isSorted(unsorted) should be(false)
  }
}

object NopSorting extends Sorting {
  def name: String = "Nop"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = ???
}