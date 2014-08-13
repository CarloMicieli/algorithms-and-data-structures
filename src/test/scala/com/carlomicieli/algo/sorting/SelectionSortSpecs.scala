package com.carlomicieli.algo.sorting

import org.scalatest._

/**
 * @author Carlo Micieli
 */
class SelectionSortSpecs extends FunSpec with ShouldMatchers {

  val numbers = Array(794, 978, 449, 540, 469, 443, 119, 10, 110, 324)
  val selection = new SelectionSort

  describe("SelectionSort") {
    it("should sort array of numbers") {
      selection.sort(numbers)
      selection.isSorted(numbers) should be(true)
    }
  }

}
