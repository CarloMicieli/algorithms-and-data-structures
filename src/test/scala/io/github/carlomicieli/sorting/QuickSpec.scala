package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class QuickSpec extends FlatSpec with Matchers {

  "Quick Sort" should "sort an array" in {
    val array = "QUICKSORTEXAMPLE".toCharArray
    Quick.sort(array)

    array.isSorted should be(true)
    array.asString should be("ACEEIKLMOPQRSTUX")
  }
}
