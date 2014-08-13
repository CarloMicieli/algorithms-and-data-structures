package com.carlomicieli.algo.sorting

import org.scalatest._

/**
 * @author Carlo Micieli
 */
class ShellSortSpecs extends FunSpec with ShouldMatchers {

  val shell = new ShellSort

  describe("ShellSort") {
    it("should use Knuth's interval sequence") {
      val seq = shell.interval(364)
      seq should be (Seq(364, 121, 40, 13, 4, 1))
    }

    it("should sort an array of numbers") {
      val numbers = Array(7, 10, 1, 9, 2, 5, 8, 6, 4, 3)
      shell.sort(numbers)
      shell.isSorted(numbers) should be(true)
    }
  }
}
