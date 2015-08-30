package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.test.AbstractTestSpec

class FixedCapacityQueueSpec extends AbstractTestSpec {

  "An empty fixed queue" should "have size equals to 0" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.isEmpty shouldBe true
    queue.size shouldBe 0
  }

  "enqueue an item" should "increase the queue size by 1" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.enqueue(1)

    queue.size shouldBe 1
    queue.isEmpty shouldBe false
    queue.peek.get shouldBe 1
  }

  "dequeue an item" should "happen in FIFO fashion in a queue" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.enqueue(1)
    queue.enqueue(2)

    val k = queue.dequeue()

    k shouldBe 1
    queue.size shouldBe 1
    queue.isEmpty shouldBe false
  }

  "A fixed capacity queue" should "reuse the released space" in {
    val queue = FixedCapacityQueue[Int](4)
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    queue.dequeue()
    queue.enqueue(5)

    queue.peek.get shouldBe 2
    queue.size shouldBe 4
  }

  "An equal number of enqueue and dequeue operations" should "leave the queue empty" in {
    val queue = FixedCapacityQueue[Int](2)
    queue.enqueue(1)
    queue.dequeue()
    queue.enqueue(2)
    queue.dequeue()
    queue.enqueue(3)
    queue.dequeue()

    queue.size shouldBe 0
    queue.isEmpty shouldBe true
    queue.peek shouldBe None
  }
}
