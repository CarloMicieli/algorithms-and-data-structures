package com.carlomicieli.algo.sorting

import org.scalatest.{FunSpec, ShouldMatchers, FunSuite}

/**
 * @author Carlo Micieli
 */
class BubbleSortSpecs extends FunSpec with ShouldMatchers {

  val numbers = Array(794, 978, 449, 540, 469, 443, 119, 10, 110, 324)
  val bubble = new BubbleSort

  describe("BubbleSort)") {
    it("should sort array") {
      bubble.sort(numbers)
      bubble.isSorted(numbers) should be(true)
    }

    it("should sort array with all the same value") {
      val sameNumber = Array(12, 12, 12, 12, 12)
      bubble.sort(sameNumber)
      bubble.isSorted(sameNumber) should be(true)
    }
  }
}
