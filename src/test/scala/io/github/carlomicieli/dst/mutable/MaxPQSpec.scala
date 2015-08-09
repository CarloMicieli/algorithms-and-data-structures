package io.github.carlomicieli.dst.mutable

import org.scalatest.{Matchers, FlatSpec}

class MaxPQSpec extends FlatSpec with Matchers {

  "A MaxPQ" should "have heap size = 0 when empty" in {
    val pq = MaxPQ[Int](16)
    pq.heapSize should be(0)
    pq.length should be(16)
  }

  "An array" should "be used to create a MaxPQ" in {
    val pq = MaxPQ(Array(4, 1, 3, 2, 16, 9, 10, 14, 8, 7))
    pq.heapSize should be(10)
    pq.toString should be("MaxPQ(16, 14, 10, 8, 7, 9, 3, 2, 4, 1)")
  }
}
