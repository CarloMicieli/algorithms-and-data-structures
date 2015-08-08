package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class InsertionSpec extends FlatSpec with Matchers {

  "Insertion Sort" should "sort an array" in {
    val array = "INSERTIONSORTEXAMPLE".toCharArray
    Insertion.sort(array)

    array.isSorted should be(true)
    array.asString should be("AEEEIILMNNOOPRRSSTTX")
  }
}
