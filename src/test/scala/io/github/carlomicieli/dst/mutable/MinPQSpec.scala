package io.github.carlomicieli.dst.mutable

import org.scalatest.{Matchers, FlatSpec}

class MinPQSpec extends FlatSpec with Matchers {
  "An empty MinPQ" should "have size 0" in {
    val pq = MinPQ(16)
    pq.isEmpty should be(true)
    pq.size should be(0)
  }
}
