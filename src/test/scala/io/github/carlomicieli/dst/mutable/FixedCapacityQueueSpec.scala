package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.Good
import org.scalatest.{Matchers, FlatSpec}

class FixedCapacityQueueSpec extends FlatSpec with Matchers {

  "An empty fixed queue" should "have size equals to 0" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.isEmpty should be(true)
    queue.size should be(0)
  }

  "enqueue an item" should "increase the queue size by 1" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.enqueue(1)

    queue.size should be(1)
    queue.isEmpty should be(false)
    queue.peek.get should be(1)
  }

  "dequeue an item" should "happen in FIFO fashion in a queue" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.enqueue(1)
    queue.enqueue(2)

    val res = queue.dequeue
    val Good((k, _)) = res

    k should be(1)
    queue.size should be(1)
    queue.isEmpty should be(false)
  }

  "A fixed capacity queue" should "reuse the released space" in {
    val queue = FixedCapacityQueue[Int](4)
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    queue.dequeue
    queue.enqueue(5)

    queue.peek.get should be(2)
    queue.size should be(4)
  }

  "An equal number of enqueue and dequeue operations" should "leave the queue empty" in {
    val queue = FixedCapacityQueue[Int](2)
    queue.enqueue(1)
    queue.dequeue
    queue.enqueue(2)
    queue.dequeue
    queue.enqueue(3)
    queue.dequeue

    queue.size should be(0)
    queue.isEmpty should be(true)
    queue.peek should be(None)
  }
}
