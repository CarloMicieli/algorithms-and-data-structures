package io.github.carlomicieli.dst

import io.github.carlomicieli.util.Or

trait Queue[A] {
  def peek: Option[A]
  def enqueue(el: A): Queue[A] Or FullQueueException
  def dequeue: (A, Queue[A]) Or EmptyQueueException
  def size: Int
  def isEmpty: Boolean
}

class InvalidQueueOperation[A](queue: Queue[A], el: A, err: Exception)
class EmptyQueueException extends Exception("Queue is empty")
class FullQueueException extends Exception("Queue is full")

sealed trait QueueOp[+A]
case class Enquque[A](el: A) extends QueueOp[A]
case object DequeueOp extends QueueOp[Nothing]

object QueueOp {
  def sequence[A](initial: Queue[A], ops: Seq[QueueOp[A]]): Queue[A] Or InvalidQueueOperation[A] = ???
}

