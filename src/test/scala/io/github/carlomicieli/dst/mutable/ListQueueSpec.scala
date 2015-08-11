package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.Good
import io.github.carlomicieli.dst.Queue
import org.scalatest.{Matchers, FlatSpec}

class ListQueueSpec extends FlatSpec with Matchers with SampleQueues {
  "An empty queue" should "have size equal to 0" in {
    val empty = ListQueue.empty[Int]
    empty.isEmpty should be(true)
    empty.size should be(0)
  }

  "Adding an element to a queue" should "happen in FIFO fashion" in {
    val queue = emptyQueue
    queue.enqueue(1)
    queue.enqueue(2)
    queue.size should be(2)
    queue.isEmpty should be(false)
  }

  "dequeue operation" should "remove elements from queue" in {
    val q = queue
    val Good((e1, _)) = q.dequeue
    e1 should be(1)

    val Good((e2, _)) = q.dequeue
    e2 should be(2)
  }

  "dequeue operation" should "return a Bad value when the queue is empty" in {
    val res = emptyQueue.dequeue
    res.isBad should be(true)
  }

  "peek operation" should "return the first element, without chaning the queue" in {
    val q = queue
    q.peek.get should be(1)
  }
}

trait SampleQueues {
  def emptyQueue = ListQueue.empty[Int]

  def queue: Queue[Int] = {
    val queue = emptyQueue
    queue.enqueue(1)
    queue.enqueue(2)
    queue
  }
}