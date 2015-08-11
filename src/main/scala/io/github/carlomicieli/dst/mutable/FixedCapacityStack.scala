package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.{EmptyStackException, FullStackException, Stack}
import io.github.carlomicieli.util.{Good, Bad, Or}

import scala.reflect.ClassTag
import scala.util.control.NoStackTrace

final class FixedCapacityStack[A] private(st: Array[A]) extends Stack[A] {
  private val storage = st
  private var top = 0

  private def isFull = storage.length == top

  def peek: Option[A] =
    if (isEmpty) None else Some(storage(top - 1))

  def push(el: A): Stack[A] Or FullStackException = {
    if (isFull)
      Bad(new FullStackException with NoStackTrace)
    else {
      storage(top) = el
      top = top + 1
      Good(this)
    }
  }

  def pop(): (A, Stack[A]) Or EmptyStackException = {
    if (isEmpty)
      Bad(new EmptyStackException with NoStackTrace)
    else {
      top = top - 1
      val e = storage(top)
      Good((e, this))
    }
  }

  def size = top

  def isEmpty = top == 0
}

object FixedCapacityStack {
  def apply[A: ClassTag](size: Int): Stack[A] = {
    val st: Array[A] = new Array[A](size)
    new FixedCapacityStack[A](st)
  }
}