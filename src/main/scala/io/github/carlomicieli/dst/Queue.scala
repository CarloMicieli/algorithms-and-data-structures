package io.github.carlomicieli.dst

import io.github.carlomicieli.util.Or

trait Queue[A] {
  def enqueue(el: A): Queue[A]
  def dequeue: (A, Queue[A]) Or EmptyQueueException
  def size: Int
  def isEmpty: Boolean
}

class InvalidQueueOperation extends Exception("Invalid queue operation")
class EmptyQueueException   extends Exception("Queue is empty")