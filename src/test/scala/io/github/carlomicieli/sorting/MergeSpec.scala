package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class MergeSpec extends FlatSpec with Matchers {

  "Merge Sort" should "sort an array" in {
    val array = "MERGESORTEXAMPLE".toCharArray

    Merge.sort(array)

    array.isSorted should be(true)
    array.asString should be("AEEEEGLMMOPRRSTX")
  }
}
