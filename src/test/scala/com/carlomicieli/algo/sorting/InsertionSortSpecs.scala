package com.carlomicieli.algo.sorting

import org.scalatest._

/**
 * @author Carlo Micieli
 */
class InsertionSortSpecs extends FunSpec with ShouldMatchers {

    val numbers = Array(794, 978, 449, 540, 469, 443, 119, 10, 110, 324)
    val insertion = new InsertionSort

    describe("BubbleSort)") {
      it("should sort array") {
        insertion.sort(numbers)
        insertion.isSorted(numbers) should be(true)
      }

      it("should sort array with all the same value") {
        val sameNumber = Array(12, 12, 12, 12, 12)
        insertion.sort(sameNumber)
        insertion.isSorted(sameNumber) should be(true)
      }
    }
  }