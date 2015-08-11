package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.{FullQueueException, EmptyQueueException, Queue}
import io.github.carlomicieli.util.{Good, Or}

import scala.util.control.NoStackTrace

final class ListQueue[A] private(st: LinkedList[A]) extends Queue[A] {
  private val storage = LinkedList.empty[A]

  def enqueue(el: A): Queue[A] Or FullQueueException = {
    storage.addBack(el)
    Good(this)
  }

  def dequeue: (A, Queue[A]) Or EmptyQueueException = {
    storage.removeHead.map {
      x => (x._1, this)
    } orElse {
      new EmptyQueueException with NoStackTrace
    }
  }

  def peek: Option[A] = storage.headOption

  def size: Int = storage.size

  def isEmpty: Boolean = storage.isEmpty
}

object ListQueue {
  def empty[A]: Queue[A] =
    new ListQueue[A](LinkedList.empty[A])
}