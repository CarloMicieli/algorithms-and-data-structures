package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class SelectionSpec extends FlatSpec with Matchers {

  "Selection Sort" should "sort an array" in {
    val array = "SELECTIONSORTEXAMPLE".toCharArray
    Selection.sort(array)

    array.isSorted should be(true)
    array.asString should be("ACEEEEILLMNOOPRSSTTX")
  }
}
