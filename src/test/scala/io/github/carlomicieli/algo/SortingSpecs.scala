package io.github.carlomicieli.algo

import org.scalatest.{ShouldMatchers, FunSuite}

/**
 * @author Carlo Micieli
 */
class SortingSpecs extends FunSuite with ShouldMatchers {

  val numbers = Array(794, 978, 449, 540, 469, 443, 119, 10, 110, 324)

  test("Sorting array with bubble sort") {
    Bubble.sort(numbers)
    Bubble.isSorted(numbers) should be (true)
  }

  test("Sorting array with insertion sort") {
    Insertion.sort(numbers)
    Insertion.isSorted(numbers) should be (true)
  }
}
