package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.{FullQueueException, EmptyQueueException, Queue}
import io.github.carlomicieli.util.{Bad, Good, Or}

import scala.reflect.ClassTag
import scala.util.control.NoStackTrace

final class FixedCapacityQueue[A](st: Array[A]) extends Queue[A] {

  private val storage = st
  private var first = -1
  private var last = 0
  private var empty = true

  def peek: Option[A] =
    if (isEmpty) None
    else Some(storage(first))

  def dequeue: (A, Queue[A]) Or EmptyQueueException = {
    if (isEmpty)
      Bad(new EmptyQueueException with NoStackTrace)
    else {
      val firstEl = storage(first)
      first = first + 1

      if (first - 1 == last) {
        empty = true
      }

      Good((firstEl, this))
    }
  }

  def size: Int = if (isEmpty) 0 else last - first

  def enqueue(el: A): Queue[A] Or FullQueueException = {
    if (isFull)
      Bad(new FullQueueException with NoStackTrace)
    else {
      if (isEmpty) {
        first = first + 1
        empty = false
      }

      storage(last) = el
      last = last + 1
      Good(this)
    }
  }

  def isEmpty: Boolean = empty

  private def isFull = storage.length == last
}

object FixedCapacityQueue {
  def apply[A: ClassTag](n: Int): Queue[A] = {
    val st = new Array[A](n)
    new FixedCapacityQueue[A](st)
  }
}