package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.test.AbstractTestSpec

class ListQueueSpec extends AbstractTestSpec with SampleQueues {
  "An empty queue" should "have size equal to 0" in {
    val empty = Queue.empty[Int]
    empty.isEmpty shouldBe true
    empty.size shouldBe 0
  }

  "Adding an element to a queue" should "happen in FIFO fashion" in {
    val queue = emptyQueue
    queue.enqueue(1)
    queue.enqueue(2)
    queue.size should be(2)
    queue.isEmpty shouldBe false
  }

  "dequeue operation" should "remove elements from queue" in {
    val q = queue
    val e1 = q.dequeue()
    e1 shouldBe 1

    val e2 = q.dequeue()
    e2 shouldBe 2
  }

  "dequeue operation" should "throw an exception when the queue is empty" in {
    val res = intercept[EmptyQueueException] {
      emptyQueue.dequeue()
    }
  }

  "peek operation" should "return the first element, without modifying the queue" in {
    val q = queue
    q.peek.get shouldBe 1
  }
}

trait SampleQueues {
  def emptyQueue = Queue.empty[Int]

  def queue: Queue[Int] = {
    val queue = emptyQueue
    queue.enqueue(1)
    queue.enqueue(2)
    queue
  }
}