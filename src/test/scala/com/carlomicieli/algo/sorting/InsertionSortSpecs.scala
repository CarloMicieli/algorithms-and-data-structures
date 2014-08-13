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

    it("should be a stable sorting algorithm") {
      val records = Array(Record(5, "First"), Record(2, "Second"), Record(5, "Third"))
      insertion.sort(records)(RecordOrdering)

      records(1).key should be(5)
      records(1).value should be("First")
      records(2).key should be(5)
      records(2).value should be("Third")
    }
  }

  case class Record(key: Int, value: String)
  object RecordOrdering extends Ordering[Record] {
    override def compare(x: Record, y: Record): Int = x.key compare y.key
  }
}