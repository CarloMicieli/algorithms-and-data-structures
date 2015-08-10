package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class HeapSpec extends FlatSpec with Matchers {

  "Heapsort" should "sort an array" in {
    val array = "HEAPSORTEXAMPLE".toCharArray
    Heap.sort(array)

    array.isSorted should be(true)
    array.asString should be("AAEEEHLMOPPRSTX")
  }
}
