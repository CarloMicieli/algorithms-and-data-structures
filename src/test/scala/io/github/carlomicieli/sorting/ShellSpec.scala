package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class ShellSpec extends FlatSpec with Matchers {

  "Shell Sort" should "sort an array" in {
    val array = "SHELLSORTEXAMPLE".toCharArray
    Shell.sort(array)

    array.isSorted should be(true)
    array.asString should be("AEEEHLLLMOPRSSTX")
  }
}
