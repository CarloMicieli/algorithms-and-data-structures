package io.github.carlomicieli.dst

import io.github.carlomicieli.util.Or

trait Queue[A] {
  def peek: Option[A]
  def enqueue(el: A): Queue[A] Or FullQueueException
  def dequeue: (A, Queue[A]) Or EmptyQueueException
  def size: Int
  def isEmpty: Boolean
}

class InvalidQueueOperation extends Exception("Invalid queue operation")
class EmptyQueueException   extends Exception("Queue is empty")
class FullQueueException   extends Exception("Queue is full")