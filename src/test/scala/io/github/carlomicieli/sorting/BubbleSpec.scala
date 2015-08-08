package io.github.carlomicieli.sorting

import org.scalatest.{Matchers, FlatSpec}

class BubbleSpec extends FlatSpec with Matchers {

  "Bubble Sort" should "sort random arrays" in {
    val array = "BUBBLESORTEXAMPLE".toCharArray
    Bubble.sort(array)

    array.asString should be("ABBBEEELLMOPRSTUX")
    Bubble.isSorted(array) should be(true)
  }

  "Bubble Sort" should "sort alredy sorted array" in {
    val array = "ABCDEFGHILM".toCharArray
    Bubble.sort(array)

    array.asString should be("ABCDEFGHILM")
  }

}

